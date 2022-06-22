package com.example.pokedex.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.domain.model.PokemonModel
import com.example.domain.model.ResultModel
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonListBinding
import com.example.pokedex.ui.adapter.PokemonAdapter.ViewHolder
import com.example.pokedex.utils.Constant
import com.example.pokedex.utils.Helper.getIdFromUrl
import com.example.pokedex.utils.ImageUtil
import com.skydoves.whatif.addAllWhatIfNotNull
import timber.log.Timber

class PokemonAdapter : Adapter<ViewHolder>() {
  private val listPokemon = ArrayList<ResultModel>()
  var clickListener: ((ResultModel, ImageView) -> Unit)? = null

  fun setListPokemon(data: PokemonModel?) {
    this.listPokemon.clear()
    this.listPokemon.addAll(data!!.results)
    Timber.d(data.toString())

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = PokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(listPokemon[position])
  }

  override fun getItemCount(): Int {
    Timber.d(listPokemon.size.toString())
    return listPokemon.size
  }

  inner class ViewHolder(private val binding: PokemonListBinding) : RecyclerView.ViewHolder(
    binding.root
  ) {
    fun bind(pokemon: ResultModel) {
      val id = getIdFromUrl(pokemon.url)
      ViewCompat.setTransitionName(binding.ivPokemon, id)
      binding.tvPokemonName.text = pokemon.name
      ImageUtil.generateImageBackgroundPalette(
        itemView.context,
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
        binding.ivPokemon,
        binding.ivPokeball
      )
      itemView.setOnClickListener {
        clickListener?.invoke(pokemon, binding.ivPokemon)
      }

//      Glide.with(itemView)
//        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
//        .into(binding.ivPokemon)
    }
  }
}