package com.example.pokedex.ui.detailpokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.pokedex.data.PokemonRepository;
import com.example.pokedex.data.local.entity.DetailPokemonEntity;
import com.example.pokedex.utils.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DetailPokemonViewModel extends ViewModel {
  private PokemonRepository repository;
  private MutableLiveData<Integer> pokemonId = new MutableLiveData<>();

  @Inject
  public DetailPokemonViewModel(PokemonRepository repository) {
    this.repository = repository;
  }

  public LiveData<Resource<DetailPokemonEntity>> pokemon = Transformations.switchMap(pokemonId,
      mPokemonId -> repository.getDetailPokemon(mPokemonId));

  public LiveData<Resource<DetailPokemonEntity>> getDetailPokemon() {
    return pokemon;
  }

  public void setPokemonId(Integer id) {
    this.pokemonId.setValue(id);
  }

  public void setCaughtPokemon(String text) {
    Resource<DetailPokemonEntity> resource = pokemon.getValue();
    if (resource != null) {
      DetailPokemonEntity pokemonDetail = resource.data;
      if (pokemonDetail != null) {
        final boolean newState = !pokemonDetail.isCaught();
        final String nickname = text;
        repository.setCaughtPokemon(pokemonDetail, newState, nickname);
      }
    }
  }
}
