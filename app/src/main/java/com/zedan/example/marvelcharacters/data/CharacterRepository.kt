package com.zedan.example.marvelcharacters.data

import androidx.paging.*
import com.zedan.example.marvelcharacters.data.local.MarvelDataBase
import com.zedan.example.marvelcharacters.data.remote.CharacterService
import com.zedan.example.marvelcharacters.di.HashKey
import com.zedan.example.marvelcharacters.domain.local.CharacterEntity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class CharacterRepository @Inject constructor(
    private val db: MarvelDataBase,
    private val remote: CharacterService,
    @HashKey private val hash: String
) {

    fun getCharacter(id: Long) = db.characterDao().getCharacter(id = id)

    fun getCharacters(
        scope: CoroutineScope
    ): Flow<PagingData<CharacterEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false,
            ),
            remoteMediator = CharactersPagingSource(
                db = db,
                remote = remote,
                hash = hash
            ),
            pagingSourceFactory = { db.characterDao().getCharacters() }
        )
            .flow
            .cachedIn(scope)
    }
}