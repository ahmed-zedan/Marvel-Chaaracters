package com.zedan.example.marvelcharacters.di

import com.zedan.example.marvelcharacters.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.math.BigInteger
import java.security.MessageDigest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HelperModule {

    @Singleton
    @Provides
    @HashKey
    fun provideHash(): String {
        val keys =
            "${BuildConfig.TIMESTAMP}${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_PUBLIC_KEY}"

        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(keys.toByteArray())).toString(16).padStart(32, '0')
    }
}