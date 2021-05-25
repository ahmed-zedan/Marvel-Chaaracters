package com.zedan.example.marvelcharacters.di

import android.content.Context
import androidx.room.Room
import com.zedan.example.marvelcharacters.data.local.MarvelDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideDB(
        @ApplicationContext applicationContext: Context
    ): MarvelDataBase {
        return Room.databaseBuilder(
            applicationContext,
            MarvelDataBase::class.java, "marvel_characters.db"
        )
            .build()
    }

}