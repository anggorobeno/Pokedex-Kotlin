package com.example.pokedex.data.local;

import android.util.Log;
import androidx.lifecycle.LiveData;

import com.example.pokedex.data.local.entity.DetailPokemonEntity;
import com.example.pokedex.data.local.entity.PokemonEntity;
import com.example.pokedex.data.local.room.PokemonDao;

import java.util.List;

import javax.inject.Inject;

public class LocalDataSource {
  private PokemonDao pokemonDao;

  @Inject
  public LocalDataSource(PokemonDao pokemonDao) {
    this.pokemonDao = pokemonDao;
  }

  public LiveData<List<DetailPokemonEntity>> getCaughtPokemon() {
    return pokemonDao.getCaughtPokemon();
  }

  public void setCaughtPokemon(DetailPokemonEntity pokemonEntity, boolean newState,
      String nickname) {
    String oldName = pokemonEntity.getName();
    String newName = oldName;

    if (nickname.isEmpty() || nickname.equals(" ")) {
      pokemonEntity.setName(oldName);
    }
    else newName = nickname;
    pokemonEntity.setNickname(nickname);
    pokemonEntity.setName(newName);
    pokemonEntity.setCaught(newState);
    pokemonDao.update(pokemonEntity);
  }

  public LiveData<List<PokemonEntity>> getListPokemon() {
    return pokemonDao.getListPokemon();
  }

  public void insertPokemon(List<PokemonEntity> pokemonEntity) {
    pokemonDao.insertPokemon(pokemonEntity);
  }

  public void insertPokemonDetail(List<DetailPokemonEntity> pokemonEntities) {
    pokemonDao.insertPokemonDetail(pokemonEntities);
  }

  public LiveData<DetailPokemonEntity> getDetailPokemon(int id) {
    return pokemonDao.getDetailPokemon(id);
  }
}
