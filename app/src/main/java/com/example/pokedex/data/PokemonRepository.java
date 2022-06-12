package com.example.pokedex.data;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.pokedex.data.local.LocalDataSource;
import com.example.pokedex.data.local.entity.DetailPokemonEntity;
import com.example.pokedex.data.local.entity.PokemonEntity;
import com.example.pokedex.data.remote.RemoteDataSource;
import com.example.pokedex.data.remote.network.ApiResponse;
import com.example.pokedex.data.remote.response.DetailPokemonResponse;
import com.example.pokedex.data.remote.response.PokemonResponse;
import com.example.pokedex.data.remote.response.ResultsItem;
import com.example.pokedex.utils.AppExecutors;
import com.example.pokedex.utils.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PokemonRepository implements IPokemonRepository {
  private RemoteDataSource remoteDataSource;
  private LocalDataSource localDataSource;
  private AppExecutors appExecutors;

  @Inject
  public PokemonRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource,
      AppExecutors appExecutors) {
    this.remoteDataSource = remoteDataSource;
    this.localDataSource = localDataSource;
    this.appExecutors = appExecutors;
  }

  @Override
  public LiveData<Resource<List<PokemonEntity>>> getPokemonList() {
    return new NetworkBoundResource<List<PokemonEntity>, PokemonResponse>(appExecutors) {
      @Override
      protected LiveData<List<PokemonEntity>> loadFromDB() {
        return localDataSource.getListPokemon();
      }

      @Override
      protected Boolean shouldFetch(List<PokemonEntity> data) {
        return (data == null) || (data.size() == 0);
      }

      @Override
      protected LiveData<ApiResponse<PokemonResponse>> createCall() {
        return remoteDataSource.getListPokemon();
      }

      @Override
      protected void saveCallResult(PokemonResponse data) {
        ArrayList<PokemonEntity> pokemonList = new ArrayList<>();
        for (ResultsItem response : data.getResults()) {
          PokemonEntity pokemon = new PokemonEntity(
              0,
              false,
              response.getName(),
              response.getUrl());

          pokemonList.add(pokemon);
        }

        localDataSource.insertPokemon(pokemonList);
      }
    }.asLiveData();
  }

  @Override
  public LiveData<Resource<DetailPokemonEntity>> getDetailPokemon(int id) {
    return new NetworkBoundResource<DetailPokemonEntity, DetailPokemonResponse>(appExecutors) {
      @Override
      protected LiveData<DetailPokemonEntity> loadFromDB() {
        return localDataSource.getDetailPokemon(id);
      }

      @Override
      protected Boolean shouldFetch(DetailPokemonEntity data) {
        return data == null;
      }

      @Override
      protected LiveData<ApiResponse<DetailPokemonResponse>> createCall() {
        Log.d(TAG, "createCall: " + id);
        return remoteDataSource.getDetailPokemon(id);
      }

      @Override
      protected void saveCallResult(DetailPokemonResponse data) {
        List<DetailPokemonEntity> pokemonEntities = new ArrayList<>();
        DetailPokemonEntity pokemon = new DetailPokemonEntity(
            false,
            data.getId(),
            data.getName(),
            data.getHeight(),
            data.getWeight(),
            data.getUrl()
        );
        pokemonEntities.add(pokemon);
        localDataSource.insertPokemonDetail(pokemonEntities);
      }
    }.asLiveData();
  }

  @Override
  public LiveData<List<DetailPokemonEntity>> getCaughtPokemon() {
    return localDataSource.getCaughtPokemon();
  }

  @Override
  public void setCaughtPokemon(DetailPokemonEntity pokemonEntity, boolean state,
      String nickname) {
    appExecutors.diskIO()
        .execute(() -> localDataSource.setCaughtPokemon(pokemonEntity, state, nickname));
  }
}
