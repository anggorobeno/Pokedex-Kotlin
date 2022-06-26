package com.example.pokedex.utils

import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.pokedex.R
import io.ak1.BubbleTabBar

object Helper {
  @JvmStatic fun getIdFromUrl(url: String): String {
    return url.split("/".toRegex()).dropLast(1).last()
  }

  fun getImage(id: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
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

  fun BubbleTabBar.onNavDestinationSelected(
    itemId: Int,
    navController: NavController
  ): Boolean {
    val builder = NavOptions.Builder()
      .setLaunchSingleTop(true)
    if (navController.currentDestination!!.parent!!.findNode(itemId) is ActivityNavigator.Destination) {
      builder.setEnterAnim(R.anim.nav_default_enter_anim)
        .setExitAnim(R.anim.nav_default_exit_anim)
        .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
        .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
    } else {
      builder.setEnterAnim(R.animator.nav_default_enter_anim)
        .setExitAnim(R.animator.nav_default_exit_anim)
        .setPopEnterAnim(R.animator.nav_default_pop_enter_anim)
        .setPopExitAnim(R.animator.nav_default_pop_exit_anim)
    }
    //if (itemId == getChildAt(0).id) {
    //builder.setPopUpTo(findStartDestination(navController.graph)!!.id, true)
    // }
    builder.setPopUpTo(itemId, true)
    val options = builder.build()
    return try {
      //TODO provide proper API instead of using Exceptions as Control-Flow.
      navController.navigate(itemId, null, options)
      true
    } catch (e: IllegalArgumentException) {
      false
    }
  }
  fun convertIntToFloat(input: Int): Float{
    return input.toFloat()
  }
}