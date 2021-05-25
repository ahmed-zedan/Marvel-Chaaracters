package com.zedan.example.marvelcharacters.presentation.characterDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zedan.example.marvelcharacters.databinding.CharacterSectionItemRawBinding
import com.zedan.example.marvelcharacters.domain.models.Section

class CharacterSectionAdapter :
    ListAdapter<Section, CharacterSectionAdapter.SectionsViewHolder>(Section.DC) {


    class SectionsViewHolder(
        private val binding: CharacterSectionItemRawBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): SectionsViewHolder {
                return SectionsViewHolder(
                    CharacterSectionItemRawBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(item: Section) {
            binding.data = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionsViewHolder {
        return SectionsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SectionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

@BindingAdapter("sectionData")
fun RecyclerView.setSectionData(
    data: List<Section>? = null
) {
    if (!data.isNullOrEmpty()) {
        this.isVisible = true
        this.adapter = (this.adapter as? CharacterSectionAdapter ?: CharacterSectionAdapter())
            .apply {
                this.submitList(data)
            }
    } else {
        isGone = true
    }
}