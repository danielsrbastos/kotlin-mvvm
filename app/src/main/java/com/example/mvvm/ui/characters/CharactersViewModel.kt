package com.example.mvvm.ui.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.entities.Character
import com.example.mvvm.data.repository.CharacterRepository
import com.example.mvvm.utils.Resource
import kotlinx.coroutines.launch

class CharactersViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    var characters: MutableLiveData<Resource<List<Character>>> = MutableLiveData()

    fun getCharacters() {
        viewModelScope.launch {
            repository.getCharacters(characters)
        }
    }
}