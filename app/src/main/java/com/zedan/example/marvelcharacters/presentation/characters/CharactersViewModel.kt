package com.zedan.example.marvelcharacters.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.zedan.example.marvelcharacters.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class CharactersViewModel @Inject constructor(
    charactersRepository: CharacterRepository
) : ViewModel() {

    val characters = charactersRepository.getCharacters(scope = viewModelScope)

}