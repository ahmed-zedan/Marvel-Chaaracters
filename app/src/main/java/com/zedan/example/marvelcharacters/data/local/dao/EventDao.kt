package com.zedan.example.marvelcharacters.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zedan.example.marvelcharacters.domain.local.EventEntity

@Dao
abstract class EventDao {

    @Insert(entity = EventEntity::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(data: List<EventEntity>)

    @Query("DELETE FROM events WHERE characterId=:cId")
    abstract suspend fun clear(cId: Long)

    suspend fun insert(cId: Long, data: List<EventEntity>){
        clear(cId = cId)
        insert(data)
    }

    @Query("DELETE FROM events")
    abstract suspend fun clear()
}