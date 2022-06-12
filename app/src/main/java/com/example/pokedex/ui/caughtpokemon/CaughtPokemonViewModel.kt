package com.example.pokedex.ui.caughtpokemon

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.pokedex.data.PokemonRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.example.pokedex.data.local.entity.DetailPokemonEntity

@HiltViewModel
class CaughtPokemonViewModel @Inject constructor(private val repository: PokemonRepository) :
  ViewModel() {
  val caughtPokemon: LiveData<List<DetailPokemonEntity>>
    get() = repository.caughtPokemon
}