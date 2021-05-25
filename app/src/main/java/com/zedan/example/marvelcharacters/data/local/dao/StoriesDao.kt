package com.zedan.example.marvelcharacters.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zedan.example.marvelcharacters.domain.local.StoryEntity

@Dao
abstract class StoriesDao {

    @Insert(entity = StoryEntity::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(data: List<StoryEntity>)

    @Query("DELETE FROM stories WHERE characterId=:cId")
    abstract suspend fun clear(cId: Long)

    suspend fun insert(cId: Long, data: List<StoryEntity>){
        clear(cId = cId)
        insert(data)
    }

    @Query("DELETE FROM stories")
    abstract suspend fun clear()
}