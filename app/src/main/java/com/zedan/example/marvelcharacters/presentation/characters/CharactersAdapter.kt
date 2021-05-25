package com.zedan.example.marvelcharacters.presentation.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zedan.example.marvelcharacters.databinding.CharacterRowItemBinding
import com.zedan.example.marvelcharacters.domain.local.CharacterEntity

class CharactersAdapter(
    private val clickListener: CharacterClickListener
) :
    PagingDataAdapter<CharacterEntity, CharactersAdapter.CharacterViewHolder>(
        CharacterEntity.DC
    ) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent, clickListener)
    }

    class CharacterViewHolder private constructor(
        private val binding: CharacterRowItemBinding,
        private val clickListener: CharacterClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun create(parent: ViewGroup, listener: CharacterClickListener): CharacterViewHolder {
                return CharacterViewHolder(
                    CharacterRowItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    listener
                )
            }
        }

        fun bind(character: CharacterEntity?) {
            binding.item = character
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    interface CharacterClickListener {
        fun onCharacterClick(id: Long)
    }
}