package com.example.pokedex.data.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pokedex.data.local.entity.DetailPokemonEntity;
import com.example.pokedex.data.local.entity.PokemonEntity;

import java.util.List;

@Dao
public interface PokemonDao {
    @Query("SELECT * FROM detail_pokemon_table WHERE caught = 1")
    LiveData<List<DetailPokemonEntity>> getCaughtPokemon();

    @Update
    void updatePokemon(PokemonEntity pokemonEntity);

    @Query("SELECT * FROM list_pokemon_table")
    LiveData<List<PokemonEntity>> getListPokemon();

    @Query("SELECT * FROM detail_pokemon_table WHERE id = :id")
    LiveData<DetailPokemonEntity> getDetailPokemon(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPokemon(List<PokemonEntity> pokemonEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPokemonDetail(List<DetailPokemonEntity> pokemonEntities);

    @Update
    void update(DetailPokemonEntity pokemonEntity);
}
