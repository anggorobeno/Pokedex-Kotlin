package com.example.domain.model

data class DetailPokemonModel(
  var isCaught: Boolean = false,
  var id: String,
  var name: String? = null,
  var height: Int = 0,
  var weight: Int = 0,
  var url: String? = null,
  var nickname: String? = null,
)
