package com.example.mvvm.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val id: Int,
    val created: String,
    val gender: String,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    override fun toString(): String {
        return "Character(id=$id, created='$created', gender='$gender', image='$image', name='$name', species='$species', status='$status', type='$type', url='$url')"
    }
}