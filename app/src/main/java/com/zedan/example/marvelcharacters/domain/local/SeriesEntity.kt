package com.zedan.example.marvelcharacters.domain.local

import androidx.room.Entity
import com.zedan.example.marvelcharacters.domain.models.Section

@Entity(tableName = "series", primaryKeys = ["id", "characterId"])
data class SeriesEntity(
    override val id: Long,
    override val characterId: Long,
    override val name: String?,
): Section(id = id, characterId = characterId, name = name)
