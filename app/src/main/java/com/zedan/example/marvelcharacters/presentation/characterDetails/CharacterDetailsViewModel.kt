package com.zedan.example.marvelcharacters.presentation.characterDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.zedan.example.marvelcharacters.data.CharacterRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class CharacterDetailsViewModel @AssistedInject constructor(
    @Assisted private val characterId: Long,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val character = characterRepository.getCharacter(id = characterId).asLiveData(viewModelScope.coroutineContext)

    companion object {
        fun provideFactory(
            assistedFactory: CharacterDetailsViewModelFactory,
            characterId: Long
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return assistedFactory.create(characterId) as T
                }

            }
    }

    @AssistedFactory
    interface CharacterDetailsViewModelFactory {
        fun create(characterId: Long): CharacterDetailsViewModel
    }
}