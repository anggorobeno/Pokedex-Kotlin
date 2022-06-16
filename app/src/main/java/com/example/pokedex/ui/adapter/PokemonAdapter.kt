package com.example.pokedex.ui.adapter

import android.os.Bundle
import com.example.pokedex.utils.Helper.getIdFromUrl
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.domain.model.PokemonModel
import com.example.pokedex.R
import com.example.pokedex.databinding.PokemonListBinding
import com.example.pokedex.ui.adapter.PokemonAdapter.ViewHolder
import com.example.pokedex.utils.Constant
import com.example.pokedex.utils.ImageUtil
import java.util.ArrayList

class PokemonAdapter : Adapter<ViewHolder>() {
  private val listPokemon = ArrayList<PokemonModel>()
  private var onItemClickCallback: OnItemClickCallback? = null
  fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
    this.onItemClickCallback = onItemClickCallback
  }

  fun setListPokemon(listPokemon: List<PokemonModel>?) {
    if (listPokemon == null) return
    this.listPokemon.clear()
    this.listPokemon.addAll(listPokemon)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = PokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(listPokemon[position])
  }

  override fun getItemCount(): Int {
    return listPokemon.size
  }

  inner class ViewHolder(private val binding: PokemonListBinding) : RecyclerView.ViewHolder(
    binding.root
  ) {
    fun bind(pokemon: PokemonModel) {
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
        val bundle = Bundle()
        bundle.putInt(Constant.EXTRA_POKEMON_ID, id.toInt())
        val extras =
          FragmentNavigatorExtras(binding.ivPokemon to itemView.context.resources.getString(R.string.transition_to_detail))
        itemView.findNavController().navigate(
          R.id.detailPokemonFragment,
          bundle,
          null,
          extras
        )
      }

//      Glide.with(itemView)
//        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
//        .into(binding.ivPokemon)
    }
  }

  interface OnItemClickCallback {
    fun onItemClicked(data: PokemonModel)
  }
}