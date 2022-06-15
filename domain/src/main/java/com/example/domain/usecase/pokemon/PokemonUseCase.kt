package com.example.domain.usecase.pokemon

import androidx.lifecycle.LiveData
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonModel
import com.example.domain.utils.Resource

interface PokemonUseCase {
  fun listPokemon(): LiveData<Resource<List<PokemonModel>>>
  fun getDetailPokemon(id: Int): LiveData<Resource<DetailPokemonModel>>
  val caughtPokemon: LiveData<List<DetailPokemonModel>>
  fun setCaughtPokemon(pokemonEntity: DetailPokemonModel, state: Boolean, nickname: String)
}