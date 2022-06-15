package com.example.data.local.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "list_pokemon_table")
class PokemonEntity(
  @field:PrimaryKey(autoGenerate = true) var id: Int = 0,
  @ColumnInfo(name = "caught") var isCaught: Boolean = false,
  var name: String,
  var url: String,
)