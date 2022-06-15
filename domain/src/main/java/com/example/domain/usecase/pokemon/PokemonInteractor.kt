package com.example.domain.usecase.pokemon

import androidx.lifecycle.LiveData
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonModel
import com.example.domain.repository.IPokemonRepository
import com.example.domain.utils.Resource

class PokemonInteractor(private val repo: IPokemonRepository) : PokemonUseCase {
  override fun listPokemon(): LiveData<Resource<List<PokemonModel>>> {
    return repo.listPokemon()
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