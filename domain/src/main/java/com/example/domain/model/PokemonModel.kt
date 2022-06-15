package com.example.domain.model

data class PokemonModel
  (
  var id: Int,
  var isCaught: Boolean = false,
  var name: String,
  var url: String,
)
