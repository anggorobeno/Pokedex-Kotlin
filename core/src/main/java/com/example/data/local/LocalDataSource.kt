package com.example.data.local

import javax.inject.Inject
import com.example.data.local.room.PokemonDao
import androidx.lifecycle.LiveData
import com.example.data.local.entity.DetailPokemonEntity
import com.example.data.local.entity.PokemonEntity

class LocalDataSource @Inject constructor(private val pokemonDao: PokemonDao) {
  val caughtPokemon: LiveData<List<DetailPokemonEntity>>
    get() = pokemonDao.caughtPokemon

  fun setCaughtPokemon(
    pokemonEntity: DetailPokemonEntity, newState: Boolean,
    nickname: String
  ) {
    val name = pokemonEntity.name
    if (nickname.isEmpty() || nickname == " ") {
      pokemonEntity.name = name
    }
    pokemonEntity.nickname = nickname
    pokemonEntity.isCaught = newState
    pokemonDao.update(pokemonEntity)
  }

  val listPokemon: LiveData<List<PokemonEntity>>
    get() = pokemonDao.listPokemon

  fun insertPokemon(pokemonEntity: List<PokemonEntity>) {
    pokemonDao.insertPokemon(pokemonEntity)
  }

  fun insertPokemonDetail(pokemonEntities: List<DetailPokemonEntity>) {
    pokemonDao.insertPokemonDetail(pokemonEntities)
  }

  fun getDetailPokemon(id: Int): LiveData<DetailPokemonEntity> {
    return pokemonDao.getDetailPokemon(id)
  }
}