package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonModel
import com.example.domain.utils.Resource
import io.reactivex.Flowable

interface IPokemonRepository {
  fun listPokemon(): LiveData<Resource<PokemonModel>>
  fun getDetailPokemon(id: Int): LiveData<Resource<DetailPokemonModel>>
  val caughtPokemon: LiveData<List<DetailPokemonModel>>
  fun setCaughtPokemon(pokemonEntity: DetailPokemonModel, state: Boolean, nickname: String)
  fun getListPokemonObservable(): Flowable<PokemonModel>
}