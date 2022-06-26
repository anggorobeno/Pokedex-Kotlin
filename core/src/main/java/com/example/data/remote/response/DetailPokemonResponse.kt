package com.example.data.remote.response

import com.example.data.local.entity.DetailPokemonEntity
import com.example.data.local.entity.PokemonStatEntity
import com.example.data.local.entity.PokemonStatsEntity
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonStatModel
import com.example.domain.model.PokemonStatsModel
import com.google.gson.annotations.SerializedName

data class DetailPokemonResponse(
  @SerializedName("id")
  val id: String? = null,
  @SerializedName("name")
  val name: String? = null,
  @SerializedName("height")
  val height: Int = 0,
  @SerializedName("weight")
  val weight: Int = 0,
  @SerializedName("url")
  val url: String? = null,
  @SerializedName("stats")
  val stats: List<PokemonStatsDto>? = null
) {
  companion object {
    fun transform(response: DetailPokemonResponse): DetailPokemonEntity {
      val transformedList = arrayListOf<PokemonStatsEntity>()
      response.stats?.forEach {
        transformedList.add(PokemonStatsDto.transform(it))
      }
      return DetailPokemonEntity(
        false,
        response.id!!,
        response.name,
        response.height,
        response.weight,
        response.url,
        "",
        transformedList
      )
    }
  }
}

data class PokemonStatsDto(
  @SerializedName("base_stat")
  val baseStats: Int? = null,
  @SerializedName("stat")
  val stat: PokemonStatDto? = null
) {
  companion object {
    fun transform(dto: PokemonStatsDto): PokemonStatsEntity {
      return PokemonStatsEntity(
        dto.baseStats,
        dto.stat?.let { PokemonStatDto.transform(it) }
      )
    }
  }
}

data class PokemonStatDto(
  @SerializedName("name")
  val name: String? = null,
  @SerializedName("url")
  val url: String? = null
) {
  companion object {
    fun transform(dto: PokemonStatDto): PokemonStatEntity {
      return PokemonStatEntity(
        dto.name,
        dto.url
      )
    }
  }
}
