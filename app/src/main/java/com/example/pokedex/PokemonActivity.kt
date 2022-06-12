package com.example.pokedex

import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.pokedex.R
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI
import com.example.pokedex.R.id
import com.example.pokedex.databinding.ActivityPokemonBinding

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity() {
  private var _binding: ActivityPokemonBinding? = null
  private val binding get() = _binding!!
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityPokemonBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar!!.hide()
    val appBarConfiguration = Builder(
      id.homeFragment, id.savedPokemon, id.searchPokemon
    )
      .build()
    val navController = Navigation.findNavController(this, id.nav_host_fragment_activity_pokemon)
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    NavigationUI.setupWithNavController(binding.navView, navController)
  }
}