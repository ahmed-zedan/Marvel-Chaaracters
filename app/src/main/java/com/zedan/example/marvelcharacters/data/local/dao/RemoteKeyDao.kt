package com.zedan.example.marvelcharacters.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zedan.example.marvelcharacters.domain.local.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(entity = RemoteKeyEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE cId=:id")
    suspend fun getKeys(id: Long): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clear()
}