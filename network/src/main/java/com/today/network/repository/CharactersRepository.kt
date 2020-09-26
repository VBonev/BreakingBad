package com.today.network.repository

import com.today.network.BreakingBadApi

class CharactersRepository(private val api: BreakingBadApi) {

    suspend fun getBreakingBadCharacters() =
        api.getAllCharacters()

}