package com.example.pokedex.ui.listpokemon

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.pokedex.data.PokemonRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.utils.Resource

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repository: PokemonRepository) :
  ViewModel() {
  val listPokemon: LiveData<Resource<List<PokemonEntity>>>
    get() = repository.listPokemon()
}