package com.example.pokedex.data.remote.response

import com.google.gson.annotations.SerializedName
import com.example.pokedex.data.remote.response.ResultsItem

data class PokemonResponse(
  @SerializedName("next") val next: String? = null,
  @SerializedName("previous") val previous: Any? = null,
  @SerializedName("count") val count: Int = 0,
  @SerializedName("results") val results: List<ResultsItem>? = null,
)