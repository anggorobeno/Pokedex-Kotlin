package com.example.pokedex.ui.caughtpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.DetailPokemonModel
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentCaughtPokemonBinding
import com.example.pokedex.ui.adapter.CaughtPokemonAdapter
import com.example.pokedex.ui.adapter.CaughtPokemonAdapter.OnItemClickCallback
import com.example.pokedex.utils.Constant
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import timber.log.Timber.Forest

@AndroidEntryPoint
class CaughtPokemonFragment : Fragment() {
  lateinit var viewModel: CaughtPokemonViewModel
  private var _binding: FragmentCaughtPokemonBinding? = null
  private val binding get() = _binding!!
  private val adapter = CaughtPokemonAdapter()
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    viewModel = ViewModelProvider(this)[CaughtPokemonViewModel::class.java]
    _binding = FragmentCaughtPokemonBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getCaughtPokemonList()
  }

  private fun getCaughtPokemonList() {
    showRv()
    viewModel.caughtPokemon.observe(viewLifecycleOwner) { caughtPokemon: List<DetailPokemonModel> ->
      Timber.d(caughtPokemon.toString())
      adapter.setListPokemon(caughtPokemon)
      binding.listPokemon = caughtPokemon
//      binding.llEmptyState.isVisible = caughtPokemon.isEmpty() || adapter.itemCount <= 0
      adapter.notifyDataSetChanged()
    }
  }

  private fun showRv() {
    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    binding.recyclerView.setHasFixedSize(true)
    binding.recyclerView.adapter = adapter
    adapter.setOnItemClickCallback(object : OnItemClickCallback {
      override fun onItemClicked(data: DetailPokemonModel?) {
        val bundle = Bundle()
        val id = data!!.id.toInt()
        bundle.putInt(Constant.EXTRA_POKEMON_ID, id)
        Navigation.findNavController(requireView()).navigate(R.id.detailPokemonFragment, bundle)
      }
    })
  }
}