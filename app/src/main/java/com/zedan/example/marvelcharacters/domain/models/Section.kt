package com.zedan.example.marvelcharacters.domain.models

import androidx.recyclerview.widget.DiffUtil

open class Section(
    open val id: Long,
    open val characterId: Long,
    open val name: String?,
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Section) return false

        if (id != other.id) return false
        if (characterId != other.characterId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + characterId.hashCode()
        return result
    }

    object DC: DiffUtil.ItemCallback<Section>(){
        override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem.id == newItem.id
        }
    }
}