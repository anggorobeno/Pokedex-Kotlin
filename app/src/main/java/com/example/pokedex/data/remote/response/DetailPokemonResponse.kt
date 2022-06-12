package com.example.pokedex.data.remote.response

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
  val url: String? = null
)