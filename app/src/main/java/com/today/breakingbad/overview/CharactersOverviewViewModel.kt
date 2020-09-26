package com.today.breakingbad.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.today.network.NetworkClient
import com.today.network.model.Character
import kotlinx.coroutines.launch

class CharactersOverviewViewModel : ViewModel() {

    private var mutableCharactersResult = MutableLiveData<List<Character>>()
    val charactersList: LiveData<List<Character>> = mutableCharactersResult
    private var originalList = listOf<Character>()

    private var mutableErrorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = mutableErrorLiveData

    fun fetchCharacters() {
        viewModelScope.launch {
            try {
                originalList = NetworkClient.charactersRepository.getBreakingBadCharacters()
                mutableCharactersResult.value = originalList

            } catch (exception: Exception) {
                mutableErrorLiveData.value = exception.localizedMessage
            }
        }
    }

    fun filterBySeason(seasonNumber: Int) {
        if (seasonNumber == 0) {
            mutableCharactersResult.value = originalList
        } else {
            val newList = originalList.filter { character ->
                character.appearance?.contains(seasonNumber) ?: false
            }
            mutableCharactersResult.value = newList
        }
    }

    fun searchByName(name: String?) {
        name?.let {
            if (name.isNotEmpty()) {
                val newList =
                    originalList.filter { it.name?.contains(name, ignoreCase = true) == true }
                mutableCharactersResult.value = newList
            }
        }
    }

}