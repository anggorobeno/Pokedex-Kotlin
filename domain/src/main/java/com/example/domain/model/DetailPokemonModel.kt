package com.example.domain.model

data class DetailPokemonModel(
  var isCaught: Boolean = false,
  var id: String,
  var name: String? = null,
  var height: Int = 0,
  var weight: Int = 0,
  var url: String? = null,
  var nickname: String? = null,
  val stats: List<PokemonStatsModel>? = null
)

data class PokemonStatsModel(
  val baseStats: Int? = null,
  val stat: PokemonStatModel? = null
){
//  companion object{
//    fun transform(model: PokemonStatsModel): PokemonStats
//  }
}

data class PokemonStatModel(
  val name: String? = null,
  val url: String? = null
){
  companion object{
    fun transform(model: PokemonStatModel): PokemonStatModel{
      return PokemonStatModel(
        model.name,
        model.url
      )
    }

  }
}
