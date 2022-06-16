package com.example.pokedex

import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.pokedex.R
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration.Builder
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.example.pokedex.R.id
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.utils.Helper.onNavDestinationSelected
import com.skydoves.whatif.whatIfNotNull

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity() {
  private var _binding: ActivityPokemonBinding? = null
  private val binding get() = _binding!!
  private lateinit var navController: NavController

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menu.whatIfNotNull {
      menuInflater.inflate(R.menu.bottom_nav_menu, it)
      binding.navView.setupWithNavController(it, navController)
    }
    return true
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)
    _binding = ActivityPokemonBinding.inflate(layoutInflater)
    setContentView(binding.root)
    supportActionBar!!.hide()
    navController = findNavController(id.nav_host_fragment_activity_pokemon)
  }
}