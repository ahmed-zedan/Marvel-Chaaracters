package com.zedan.example.marvelcharacters.domain.local

import androidx.room.Entity
import com.zedan.example.marvelcharacters.domain.models.Section


@Entity(tableName = "events", primaryKeys = ["id", "characterId"])
data class EventEntity(
    override val id: Long,
    override val characterId: Long,
    override val name: String?
): Section(id = id, characterId = characterId, name = name)