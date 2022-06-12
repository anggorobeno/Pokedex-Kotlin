package com.example.pokedex.data.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokedex.data.local.entity.DetailPokemonEntity;
import com.example.pokedex.data.local.entity.PokemonEntity;

import javax.inject.Singleton;


@Database(entities = {PokemonEntity.class, DetailPokemonEntity.class},
        version = 1,
        exportSchema = false)
@Singleton
public abstract class PokemonDB extends RoomDatabase {
    public abstract PokemonDao pokemonDao();

    private static volatile PokemonDB INSTANCE;


    public static PokemonDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonDB.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PokemonDB.class, "Academies.db")
                        .build();
            }
        }
        return INSTANCE;
    }
}