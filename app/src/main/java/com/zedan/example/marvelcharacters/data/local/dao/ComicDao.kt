package com.zedan.example.marvelcharacters.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zedan.example.marvelcharacters.domain.local.ComicEntity

@Dao
abstract class ComicDao {

    @Insert(entity = ComicEntity::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(data: List<ComicEntity>)

    @Query("DELETE FROM comics WHERE characterId=:cId")
    abstract suspend fun clear(cId: Long)

    suspend fun insert(cId: Long, data: List<ComicEntity>){
        clear(cId = cId)
        insert(data)
    }

    @Query("DELETE FROM comics")
    abstract suspend fun clear()
}