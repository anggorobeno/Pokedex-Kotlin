package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
  @SerializedName("next") val next: String? = null,
  @SerializedName("previous") val previous: Any? = null,
  @SerializedName("count") val count: Int = 0,
  @SerializedName("results") val results: List<ResultsItem>? = null,
)