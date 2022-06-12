package com.example.pokedex.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_pokemon_table")
data class DetailPokemonEntity(
  @ColumnInfo(name = "caught") var isCaught: Boolean = false,
  @PrimaryKey var id: String,
  var name: String? = null,
  var height: Int = 0,
  var weight: Int = 0,
  var url: String? = null,
  var nickname: String? = null,
  ) {

}