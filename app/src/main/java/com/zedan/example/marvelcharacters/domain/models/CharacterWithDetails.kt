package com.zedan.example.marvelcharacters.domain.models

import androidx.room.Embedded
import androidx.room.Relation
import com.zedan.example.marvelcharacters.domain.local.*

data class CharacterWithDetails(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val comics: List<ComicEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val series: List<SeriesEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val stories: List<StoryEntity>?,

    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val events: List<EventEntity>?,
)
