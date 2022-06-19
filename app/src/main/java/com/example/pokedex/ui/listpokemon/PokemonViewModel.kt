package com.example.pokedex.ui.listpokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.PokemonModel
import com.example.domain.usecase.pokemon.PokemonUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val useCase: PokemonUseCase) :
  ViewModel() {
  val listPokemon: LiveData<Resource<PokemonModel>>
    get() = useCase.listPokemon()

  fun getPokemon(): Flowable<PokemonModel> {
    return useCase.getObservable()

  }
}