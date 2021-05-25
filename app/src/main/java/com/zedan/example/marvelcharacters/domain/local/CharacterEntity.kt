package com.zedan.example.marvelcharacters.domain.local

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zedan.example.marvelcharacters.domain.models.Image

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Long,
    val name: String?,
    @Embedded val image: Image?,
){
    object DC : DiffUtil.ItemCallback<CharacterEntity>(){
        override fun areContentsTheSame(
            oldItem: CharacterEntity,
            newItem: CharacterEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CharacterEntity, newItem: CharacterEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
