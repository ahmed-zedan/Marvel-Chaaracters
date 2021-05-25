package com.zedan.example.marvelcharacters.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.zedan.example.marvelcharacters.domain.local.CharacterEntity
import com.zedan.example.marvelcharacters.domain.models.CharacterWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(entity = CharacterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: CharacterEntity)

    @Insert(entity = CharacterEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters ORDER BY name ASC")
    fun getCharacters(): PagingSource<Int, CharacterEntity>

    @Transaction
    @Query("SELECT * FROM characters WHERE id=:id")
    fun getCharacter(id: Long): Flow<CharacterWithDetails>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}