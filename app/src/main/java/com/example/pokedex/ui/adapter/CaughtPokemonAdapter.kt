package com.example.pokedex.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.local.entity.DetailPokemonEntity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.PokemonListBinding
import com.example.pokedex.ui.adapter.CaughtPokemonAdapter.ViewHolder
import com.example.pokedex.utils.ImageUtil
import java.util.ArrayList

class CaughtPokemonAdapter : Adapter<ViewHolder>() {
  private val listPokemon = ArrayList<DetailPokemonEntity>()
  fun setListPokemon(listPokemon: List<DetailPokemonEntity>) {
    this.listPokemon.clear()
    this.listPokemon.addAll(listPokemon)
    notifyDataSetChanged()
  }

  private var onItemClickCallback: OnItemClickCallback? = null
  fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
    this.onItemClickCallback = onItemClickCallback
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = PokemonListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(listPokemon[position])
    holder.itemView.setOnClickListener { view: View? ->
      onItemClickCallback!!.onItemClicked(
        listPokemon[holder.bindingAdapterPosition]
      )
    }
  }

  override fun getItemCount(): Int {
    return listPokemon.size
  }

  inner class ViewHolder(var binding: PokemonListBinding) : RecyclerView.ViewHolder(
    binding.root
  ) {
    fun bind(pokemon: DetailPokemonEntity) {
      val name = if (pokemon.nickname == null
        || pokemon.nickname == ""
      ) pokemon.name else pokemon.nickname!!
      binding.tvPokemonName.text = name
      ImageUtil.generateBackgroundPalette(
        itemView.context,
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.id + ".png",
        binding.ivPokemon,
        binding.ivPokeball
      )
    }
  }

  interface OnItemClickCallback {
    fun onItemClicked(data: DetailPokemonEntity?)
  }
}