package com.zedan.example.marvelcharacters.data.remote

import com.zedan.example.marvelcharacters.BuildConfig
import com.zedan.example.marvelcharacters.domain.remote.CharacterResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("v1/public/characters")
    fun getCharactersAsync(
        @Query("apikey") apiKey: String = BuildConfig.MARVEL_PUBLIC_KEY,
        @Query("ts") timestamp: String = "${BuildConfig.TIMESTAMP}",
        @Query("orderBy") name: String = "name",
        @Query("hash") hash: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): Deferred<CharacterResponse>

}