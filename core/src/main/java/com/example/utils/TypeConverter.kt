package com.example.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.data.local.entity.PokemonStatsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
  val gson = Gson()

  @TypeConverter
  fun statToString(stat: List<PokemonStatsEntity>): String {
    return gson.toJson(stat,object: TypeToken<ArrayList<PokemonStatsEntity>>() {}.type) ?: "[]"
  }
  @TypeConverter
  fun stringToStat(data: String): List<PokemonStatsEntity> {
    return Gson().fromJson(data, object : TypeToken<ArrayList<PokemonStatsEntity>>() {}.type) ?: emptyList()
  }
}