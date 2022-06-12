package com.example.pokedex.utils

object Helper {
  @JvmStatic fun getIdFromUrl(url: String): String {
    return url.substring(url.lastIndexOf('/') - 2, url.lastIndexOf('/'))
  }

  @JvmStatic fun getWeight(weight: Double): String {
    return "${weight / 10} Kg"
  }

  @JvmStatic fun getHeight(height: Double): String {
    return "${height / 10} m"
  }

  @JvmStatic fun idConverter(id: Int): String {
    return String.format("%03d", id)
  }
}