package com.today.network

import com.today.network.model.Character
import retrofit2.http.GET

interface BreakingBadApi {
    @GET("characters")
    suspend fun getAllCharacters(): List<Character>
}