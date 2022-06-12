package com.example.pokedex.data.local.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "list_pokemon_table")
class PokemonEntity(
  @field:PrimaryKey(autoGenerate = true) var id: Int,
  @ColumnInfo(name = "caught") var isCaught: Boolean = false,
  var name: String,
  var url: String,
)