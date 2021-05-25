package com.zedan.example.marvelcharacters.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.zedan.example.marvelcharacters.data.local.MarvelDataBase
import com.zedan.example.marvelcharacters.data.remote.CharacterService
import com.zedan.example.marvelcharacters.domain.local.CharacterEntity
import com.zedan.example.marvelcharacters.domain.local.RemoteKeyEntity
import com.zedan.example.marvelcharacters.domain.models.CharacterWithDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

@ExperimentalPagingApi
class CharactersPagingSource constructor(
    private val db: MarvelDataBase,
    private val remote: CharacterService,
    private val hash: String
): RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(state.config.pageSize) ?: START_OFFSET
            }
            LoadType.PREPEND -> {
                val remoteKeys = getKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = withContext(IO){
                remote
                    .getCharactersAsync(
                        hash = hash,
                        offset = page,
                        limit = state.config.pageSize
                    ).await()
            }

            val charactersResponse = response.data?.results ?: emptyList()
            val characters =
                withContext(Dispatchers.Default) { charactersResponse.map { cr -> cr.toCharacterEntity() } }
            val endOfPaginationReached = characters.isEmpty()
            db.withTransaction {

                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    clearDatabase()
                }

                val prevKey =
                    if (page == START_OFFSET) null else (page - (response.data?.limit ?: state.config.pageSize))
                val nextKey =
                    if (endOfPaginationReached) null else (page + (response.data?.limit ?: state.config.pageSize))
                val keys = withContext(Dispatchers.Default) {
                    characters.map {
                        RemoteKeyEntity(cId = it.character.id, prevKey = prevKey, nextKey = nextKey)
                    }
                }
                insert(character = characters)
                db.remoteDao().insert(keys = keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Throwable) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun clearDatabase(){
        db.remoteDao().clear()
        db.characterDao().clearCharacters()
        db.comicDao().clear()
        db.eventsDao().clear()
        db.seriesDao().clear()
        db.storiesDao().clear()
    }

    private suspend fun insert(character: List<CharacterWithDetails>){
        character.forEach {  c ->
            db.characterDao().insert(c.character)
            db.comicDao().insert(c.character.id, c.comics ?: emptyList())
            db.eventsDao().insert(c.character.id, c.events ?: emptyList())
            db.seriesDao().insert(c.character.id, c.series ?: emptyList())
            db.storiesDao().insert(c.character.id, c.stories ?: emptyList())
        }
    }

    private suspend fun getKeyForLastItem(
        state: PagingState<Int, CharacterEntity>
    ): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { c -> withContext(IO) {db.remoteDao().getKeys(c.id)} }
    }

    private suspend fun getKeyForFirstItem(state: PagingState<Int, CharacterEntity>): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { c -> withContext(IO) {db.remoteDao().getKeys(c.id)} }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id
                ?.let { id -> withContext(IO) {db.remoteDao().getKeys(id)} }

        }
    }

    companion object {
        const val START_OFFSET = 0
    }
}