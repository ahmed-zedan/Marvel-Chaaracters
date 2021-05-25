package com.zedan.example.marvelcharacters.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zedan.example.marvelcharacters.domain.local.SeriesEntity

@Dao
abstract class SeriesDao {

    @Insert(entity = SeriesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(data: List<SeriesEntity>)

    @Query("DELETE FROM series WHERE characterId=:cId")
    abstract suspend fun clear(cId: Long)

    suspend fun insert(cId: Long, data: List<SeriesEntity>){
        clear(cId = cId)
        insert(data)
    }

    @Query("DELETE FROM series")
    abstract suspend fun clear()
}