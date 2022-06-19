package com.example.domain.model

data class PokemonModel
  (
  var next: String? = null,
  val previous: Any? = null,
  val count: Int = 0,
  val results: List<ResultModel>
)

data class ResultModel(
  var id: Int,
  var isCaught: Boolean = false,
  var name: String,
  var url: String,
)
