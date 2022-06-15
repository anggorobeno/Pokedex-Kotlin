package com.example.data.local.room

import androidx.room.Dao
import androidx.lifecycle.LiveData
import androidx.room.Insert
import com.example.data.local.entity.DetailPokemonEntity
import androidx.room.Update
import com.example.data.local.entity.PokemonEntity
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
  @get:Query("SELECT * FROM detail_pokemon_table WHERE caught = 1")
  val caughtPokemon: LiveData<List<DetailPokemonEntity>>

  @Update
  fun updatePokemon(pokemonEntity: PokemonEntity)

  @get:Query("SELECT * FROM list_pokemon_table")
  val listPokemon: LiveData<List<PokemonEntity>>

  @Query("SELECT * FROM detail_pokemon_table WHERE id = :id")
  fun getDetailPokemon(id: Int): LiveData<DetailPokemonEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertPokemon(pokemonEntities: List<PokemonEntity>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertPokemonDetail(pokemonEntities: List<DetailPokemonEntity>)

  @Update
  fun update(pokemonEntity: DetailPokemonEntity)
}