package com.today.breakingbad.overview

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.today.network.NetworkClient
import com.today.network.model.Character
import kotlinx.coroutines.launch

class CharactersOverviewViewModel : ViewModel() {

    @VisibleForTesting
    var originalList = listOf<Character>()
    private var mutableCharactersResult = MutableLiveData<List<Character>>()
    val charactersList: LiveData<List<Character>> = mutableCharactersResult

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
            mutableCharactersResult.value = originalList.filter { character ->
                character.appearance?.contains(seasonNumber) ?: false
            }
        }
    }

    fun searchByName(name: String?) {
        if (!name.isNullOrBlank()) {
            mutableCharactersResult.value =
                originalList.filter { it.name?.contains(name, ignoreCase = true) == true }
        }
    }

}