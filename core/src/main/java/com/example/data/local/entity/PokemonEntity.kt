package com.example.data.local.entity

import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.domain.model.ResultModel

@Entity(tableName = "list_pokemon_table")
class PokemonEntity(
  @field:PrimaryKey(autoGenerate = true) var id: Int = 0,
  @ColumnInfo(name = "caught") var isCaught: Boolean = false,
  var name: String,
  var url: String,
){
  companion object{
    fun transform(entity: PokemonEntity): ResultModel {
      return ResultModel(
        entity.id,
        false,
        entity.name,
        entity.url
      )
    }
  }
}