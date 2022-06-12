package com.example.pokedex.data

import androidx.lifecycle.LiveData
import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.data.local.entity.DetailPokemonEntity
import com.example.pokedex.utils.Resource

interface IPokemonRepository {
  fun listPokemon(): LiveData<Resource<List<PokemonEntity>>>
  fun getDetailPokemon(id: Int): LiveData<Resource<DetailPokemonEntity>>
  val caughtPokemon: LiveData<List<DetailPokemonEntity>>
  fun setCaughtPokemon(pokemonEntity: DetailPokemonEntity, state: Boolean, nickname: String)
}