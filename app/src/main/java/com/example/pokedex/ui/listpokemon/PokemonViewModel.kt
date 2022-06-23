package com.example.pokedex.ui.listpokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.model.PokemonModel
import com.example.domain.usecase.pokemon.PokemonUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val useCase: PokemonUseCase) :
  ViewModel() {
  val disposable = CompositeDisposable()
  val limit = 20
  val listPokemon: LiveData<Resource<PokemonModel>>
    get() = useCase.listPokemon()


  val _pokemonList = MutableLiveData<Resource<PokemonModel>>(Resource.Loading(null))
  val pokemonList: LiveData<Resource<PokemonModel>> = _pokemonList

  init {
    loadListPokemon(1)
  }
  fun loadListPokemon(page: Int){
    val offset = page * limit
    useCase.getObservable(offset)
      .subscribe(object : Observer<PokemonModel>{
        override fun onSubscribe(d: Disposable) {
          disposable.add(d)
          _pokemonList.postValue(Resource.Loading(null))
        }

        override fun onNext(t: PokemonModel) {
          _pokemonList.postValue(Resource.Success(t))
        }

        override fun onError(e: Throwable) {
          _pokemonList.postValue(Resource.Error(e.message))
        }


        override fun onComplete() {
          Timber.d("Completed")
        }
      })
  }
}