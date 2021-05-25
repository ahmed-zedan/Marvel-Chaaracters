package com.zedan.example.marvelcharacters.domain.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey
    val cId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)