package com.example.mvvm.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvm.data.entities.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAllCharacters() : List<Character>

    @Query("SELECT * FROM characters WHERE id = :id")
    fun getCharacter(id: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)
}
