package com.example.pokedex.data;

import androidx.lifecycle.LiveData;

import com.example.pokedex.data.local.entity.DetailPokemonEntity;
import com.example.pokedex.data.local.entity.PokemonEntity;
import com.example.pokedex.utils.Resource;

import java.util.List;

public interface IPokemonRepository {
    public LiveData<Resource<List<PokemonEntity>>> getPokemonList();
    public LiveData<Resource<DetailPokemonEntity>> getDetailPokemon(int id);
    public LiveData<List<DetailPokemonEntity>> getCaughtPokemon();
    public void setCaughtPokemon(DetailPokemonEntity pokemonEntity, boolean state,String nickname);

}
