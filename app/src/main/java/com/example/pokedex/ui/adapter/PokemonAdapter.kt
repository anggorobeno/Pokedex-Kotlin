package com.example.pokedex.ui.adapter

import com.example.pokedex.utils.Helper.getIdFromUrl
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.data.local.entity.PokemonEntity
import android.content.ContentValues
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.PokemonListBinding
import com.example.pokedex.ui.adapter.PokemonAdapter.ViewHolder
import java.util.ArrayList

class PokemonAdapter : Adapter<ViewHolder>() {
  private val listPokemon = ArrayList<PokemonEntity>()
  private var onItemClickCallback: OnItemClickCallback? = null
  fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
    this.onItemClickCallback = onItemClickCallback
  }

  fun setListPokemon(listPokemon: List<PokemonEntity>?) {
    Log.d(ContentValues.TAG, "setListPokemon: " + listPokemon.toString())
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
    holder.itemView.setOnClickListener { view: View? ->
      onItemClickCallback!!.onItemClicked(
        listPokemon[holder.bindingAdapterPosition]
      )
    }
  }

  override fun getItemCount(): Int {
    return listPokemon.size
  }

  inner class ViewHolder(private val binding: PokemonListBinding) : RecyclerView.ViewHolder(
    binding.root
  ) {
    fun bind(pokemon: PokemonEntity) {
      val id = getIdFromUrl(pokemon.url)
      binding.tvPokemonName.text = pokemon.name
      Glide.with(itemView)
        .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png")
        .into(binding.ivPokemon)
    }
  }

  interface OnItemClickCallback {
    fun onItemClicked(data: PokemonEntity)
  }
}