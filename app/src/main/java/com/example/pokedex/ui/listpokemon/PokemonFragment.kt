package com.example.pokedex.ui.listpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.databinding.FragmentPokemonBinding
import com.example.pokedex.ui.adapter.PokemonAdapter
import com.example.pokedex.ui.adapter.PokemonAdapter.OnItemClickCallback
import com.example.pokedex.utils.Constant
import com.example.pokedex.utils.Helper.getIdFromUrl
import com.example.pokedex.utils.Resource
import com.example.pokedex.utils.Status.ERROR
import com.example.pokedex.utils.Status.LOADING
import com.example.pokedex.utils.Status.SUCCESS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonFragment : Fragment() {
  private var viewModel: PokemonViewModel? = null
  private var _binding: FragmentPokemonBinding? = null
  private val binding get() = _binding!!
  private val adapter = PokemonAdapter()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
    _binding = FragmentPokemonBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getPokemonList()
  }

  private fun getPokemonList() {
    viewModel!!.listPokemon.observe(viewLifecycleOwner) { pokemonResponse: Resource<List<PokemonEntity>> ->
      when (pokemonResponse.status) {
        LOADING -> binding.progressBar.visibility = View.VISIBLE
        SUCCESS -> {
          binding.progressBar.visibility = View.GONE
          adapter.setListPokemon(pokemonResponse.data)
          adapter.notifyDataSetChanged()
          showRv()
        }
        ERROR -> {
          binding.progressBar.visibility = View.GONE
          Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun showRv() {
    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    binding.recyclerView.setHasFixedSize(true)
    binding.recyclerView.adapter = adapter
    adapter.setOnItemClickCallback(object : OnItemClickCallback {
      override fun onItemClicked(data: PokemonEntity) {
        val bundle = Bundle()
        val id = getIdFromUrl(data.url).toInt()
        bundle.putInt(Constant.EXTRA_POKEMON_ID, id)
        Navigation.findNavController(requireView()).navigate(R.id.detailPokemonFragment, bundle)
      }
    })
  }
}