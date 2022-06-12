package com.example.pokedex.ui.detailpokemon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(private val repository: PokemonRepository) :
  ViewModel() {
  private val pokemonId = MutableLiveData<Int>()
  var detailPokemon = Transformations.switchMap(
    pokemonId
  ) { mPokemonId: Int? ->
    repository.getDetailPokemon(
      mPokemonId!!
    )
  }

  fun setPokemonId(id: Int) {
    pokemonId.value = id
  }

  fun setCaughtPokemon(text: String) {
    val resource = detailPokemon.value
    if (resource != null) {
      val pokemonDetail = resource.data
      if (pokemonDetail != null) {
        val newState = !pokemonDetail.isCaught
        repository.setCaughtPokemon(pokemonDetail, newState, text)
      }
    }
  }

  val getRandomLogic: Int
    get() {
      val random = Random()
      return random.nextInt(3 - 1) + 1
    }
}