package com.example.data.remote.response

import com.example.domain.model.PokemonModel
import com.example.domain.model.ResultModel
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
  @SerializedName("next") val next: String? = null,
  @SerializedName("previous") val previous: Any? = null,
  @SerializedName("count") val count: Int = 0,
  @SerializedName("results") val results: List<ResultsItem>? = null,
) {
  companion object {
    fun transform(response: PokemonResponse): PokemonModel {
      val transformedList = arrayListOf<ResultModel>()
      response.results?.forEach {
        transformedList.add(ResultsItem.transform(it))
      }
      return PokemonModel(
        response.next,
        response.previous,
        response.count,
        transformedList
      )

    }

  }

  data class ResultsItem(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null,
  ) {
    companion object {
      fun transform(dto: ResultsItem): ResultModel {
        return ResultModel(
          0,
          false,
          dto.name!!,
          dto.url!!
        )
      }
    }
  }
}