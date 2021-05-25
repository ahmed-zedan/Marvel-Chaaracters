package com.zedan.example.marvelcharacters.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zedan.example.marvelcharacters.R
import com.zedan.example.marvelcharacters.databinding.CharactersLoadStateFooterViewItemBinding

class CharactersLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<CharactersLoadStateAdapter.CharactersLoadStateViewHolder>(){

    companion object{
        const val STATE_NOT_LOAD = -1
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return if (loadState is LoadState.NotLoading) -4 else STATE_NOT_LOAD
    }

    class CharactersLoadStateViewHolder(
        private val binding: CharactersLoadStateFooterViewItemBinding,
        retry: () -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressCircularFooterCharacter.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }


        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.characters_load_state_footer_view_item, parent, false)
                val binding = CharactersLoadStateFooterViewItemBinding.bind(view)
                return CharactersLoadStateViewHolder(binding, retry)
            }
        }
    }

    override fun onBindViewHolder(holder: CharactersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharactersLoadStateViewHolder {
        return CharactersLoadStateViewHolder.create(parent, retry)
    }
}