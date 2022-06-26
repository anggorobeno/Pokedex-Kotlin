package com.example.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.remote.response.PokemonStatDto
import com.example.data.remote.response.PokemonStatsDto
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonStatModel
import com.example.domain.model.PokemonStatsModel

@Entity(tableName = "detail_pokemon_table")
data class DetailPokemonEntity(
  @ColumnInfo(name = "caught")
  var isCaught: Boolean = false,
  @PrimaryKey
  var id: String,
  var name: String? = null,
  var height: Int = 0,
  var weight: Int = 0,
  var url: String? = null,
  var nickname: String? = null,
  val stats: List<PokemonStatsEntity>
) {
  companion object {
    fun transform(entity: DetailPokemonEntity): DetailPokemonModel {
      val transformedList = arrayListOf<PokemonStatsModel>()
      entity.stats.forEach {
        transformedList.add(PokemonStatsEntity.transform(it))
      }
      return DetailPokemonModel(
        false,
        entity.id,
        entity.name,
        entity.height,
        entity.weight,
        entity.url,
        entity.nickname,
        transformedList
      )
    }
  }

}

data class PokemonStatsEntity(
  val baseStats: Int? = null,
  val stat: PokemonStatEntity? = null
) {
  companion object {
    fun transform(entity: PokemonStatsEntity): PokemonStatsModel {
      return PokemonStatsModel(
        entity.baseStats,
        entity.stat?.let { PokemonStatEntity.transform(it) }
      )
    }
  }
}

data class PokemonStatEntity(
  val name: String? = null,
  val url: String? = null
) {
  companion object {
    fun transform(entity: PokemonStatEntity): PokemonStatModel {
      return PokemonStatModel(
        entity.name,
        entity.url
      )
    }
  }
}
