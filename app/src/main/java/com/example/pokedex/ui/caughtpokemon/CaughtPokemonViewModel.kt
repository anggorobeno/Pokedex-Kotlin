package com.example.pokedex.ui.caughtpokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.DetailPokemonModel
import com.example.domain.usecase.pokemon.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CaughtPokemonViewModel @Inject constructor(private val usecase: PokemonUseCase) :
  ViewModel() {
  val caughtPokemon: LiveData<List<DetailPokemonModel>>
    get() = usecase.caughtPokemon
}