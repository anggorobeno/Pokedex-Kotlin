package com.example.pokedex.data.local.room

import android.content.Context
import androidx.room.Database
import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.data.local.entity.DetailPokemonEntity
import javax.inject.Singleton
import androidx.room.RoomDatabase
import com.example.pokedex.data.local.room.PokemonDao
import kotlin.jvm.Volatile
import com.example.pokedex.data.local.room.PokemonDB
import androidx.room.Room

@Database(
  entities = [PokemonEntity::class, DetailPokemonEntity::class],
  version = 1,
  exportSchema = false
)
@Singleton
abstract class PokemonDB : RoomDatabase() {
  abstract fun pokemonDao(): PokemonDao

  companion object {
    @Volatile
    private var INSTANCE: PokemonDB? = null
    @JvmStatic fun getInstance(context: Context): PokemonDB {
      if (INSTANCE == null) {
        synchronized(PokemonDB::class.java) {
          INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            PokemonDB::class.java, "Pokemon.db"
          )
            .build()
        }
      }
      return INSTANCE!!
    }
  }
}