package com.zedan.example.marvelcharacters.presentation.characterDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.zedan.example.marvelcharacters.R
import com.zedan.example.marvelcharacters.databinding.CharacterDetailsViewBinding
import com.zedan.example.marvelcharacters.widget.SpaceDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterDetailsView : Fragment() {

    private val args: CharacterDetailsViewArgs by navArgs()

    @Inject
    lateinit var characterDetailsViewModelFactory: CharacterDetailsViewModel.CharacterDetailsViewModelFactory

    private val viewModel: CharacterDetailsViewModel by viewModels {
        CharacterDetailsViewModel.provideFactory(characterDetailsViewModelFactory, args.id)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = CharacterDetailsViewBinding.inflate(inflater, container, false)

        val spaceDecoration = SpaceDecoration(resources.getDimensionPixelSize(
            R.dimen.space_recycler
        ))

        binding.characterContent.comicsNameList.addItemDecoration(spaceDecoration)
        binding.characterContent.seriesNameList.addItemDecoration(spaceDecoration)
        binding.characterContent.storiesNameList.addItemDecoration(spaceDecoration)
        binding.characterContent.eventNameList.addItemDecoration(spaceDecoration)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        return binding.root
    }
}