package com.today.network.repository

import com.today.network.BreakingBadApi
import com.today.network.model.Character

class CharactersRepository(private val api:BreakingBadApi) {

   suspend fun getBreakingBadCharacters(): List<Character>{
       return api.getAllCharacters()
   }
}