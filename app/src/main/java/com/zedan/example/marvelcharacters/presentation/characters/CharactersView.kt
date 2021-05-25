package com.zedan.example.marvelcharacters.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.zedan.example.marvelcharacters.R
import com.zedan.example.marvelcharacters.databinding.CharactersViewBinding
import com.zedan.example.marvelcharacters.widget.SpaceDecoration
import com.zedan.example.marvelcharacters.presentation.characters.CharactersLoadStateAdapter.Companion.STATE_NOT_LOAD
import com.zedan.example.marvelcharacters.presentation.characters.CharactersViewDirections.Companion.actionCharactersViewToCharacterDetailsView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class CharactersView : Fragment(), CharactersAdapter.CharacterClickListener {

    private val viewModel: CharactersViewModel by viewModels()

    private val adapter = CharactersAdapter(this)

    private var _binding: CharactersViewBinding? = null
    private val binding: CharactersViewBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CharactersViewBinding.inflate(inflater, container, false)

        val footerAdapter = CharactersLoadStateAdapter(adapter::retry)

        adapter.addLoadStateListener { loadStates ->
            footerAdapter.loadState = loadStates.append
        }

        val charactersAdapter = ConcatAdapter(
            ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build(),
            adapter,
            footerAdapter
        )

        binding.charactersList.adapter = charactersAdapter

        val manger = GridLayoutManager(context, 2)

        manger.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (charactersAdapter.getItemViewType(position) == STATE_NOT_LOAD) 2 else 1
            }
        }

        binding.charactersList.layoutManager = manger

        binding.charactersList.addItemDecoration(
            SpaceDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.space_recycler
                )
            )
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submitCharacter()

        addState()
    }

    private fun submitCharacter() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characters.collectLatest { c ->
                adapter.submitData(c)
            }
        }
    }

    private fun addState() {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { state ->
                binding.charactersList.isVisible = state.mediator?.refresh !is LoadState.Loading
                binding.progressCircular.isVisible = state.mediator?.refresh is LoadState.Loading
            }
        }
    }

    override fun onCharacterClick(id: Long) {
        findNavController().navigate(
            actionCharactersViewToCharacterDetailsView(id)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}