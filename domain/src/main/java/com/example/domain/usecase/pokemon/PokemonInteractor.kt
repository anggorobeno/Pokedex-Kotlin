package com.example.domain.usecase.pokemon

import androidx.lifecycle.LiveData
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonModel
import com.example.domain.repository.IPokemonRepository
import com.example.domain.utils.Resource
import io.reactivex.Flowable

class PokemonInteractor(private val repo: IPokemonRepository) : PokemonUseCase {
  override fun listPokemon(): LiveData<Resource<PokemonModel>> {
    return repo.listPokemon()
  }

  override fun getObservable(): Flowable<PokemonModel>{
    return repo.getListPokemonObservable()
  }

  override fun getDetailPokemon(id: Int): LiveData<Resource<DetailPokemonModel>> {
    return repo.getDetailPokemon(id)
  }

  override val caughtPokemon: LiveData<List<DetailPokemonModel>>
    get() = repo.caughtPokemon

  override fun setCaughtPokemon(
    pokemonEntity: DetailPokemonModel,
    state: Boolean,
    nickname: String
  ) {
    repo.setCaughtPokemon(pokemonEntity, state, nickname)
  }
}