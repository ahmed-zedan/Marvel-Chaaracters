package com.zedan.example.marvelcharacters.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zedan.example.marvelcharacters.data.local.dao.*
import com.zedan.example.marvelcharacters.domain.local.*


@Database(
    entities = [
        CharacterEntity::class,
        ComicEntity::class,
        StoryEntity::class,
        EventEntity::class,
        SeriesEntity::class,
        RemoteKeyEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class MarvelDataBase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun comicDao(): ComicDao
    abstract fun seriesDao(): SeriesDao
    abstract fun storiesDao(): StoriesDao
    abstract fun eventsDao(): EventDao
    abstract fun remoteDao(): RemoteKeyDao
}