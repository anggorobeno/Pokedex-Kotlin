package com.example.pokedex.ui.listpokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.domain.model.ResultModel
import com.example.domain.utils.Resource
import com.example.pokedex.R
import com.example.pokedex.R.string
import com.example.pokedex.databinding.FragmentPokemonBinding
import com.example.pokedex.ui.adapter.PokemonAdapter
import com.example.pokedex.utils.Constant
import com.example.pokedex.utils.EndlessRecyclerOnScrollListener
import com.example.pokedex.utils.Helper.getIdFromUrl
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class PokemonFragment : Fragment() {
  val viewModel: PokemonViewModel by viewModels()
  private var _binding: FragmentPokemonBinding? = null
  private val binding get() = _binding!!
  private val disposable = CompositeDisposable()
  private val adapter = PokemonAdapter()
  var currentPage = 1
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentPokemonBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    postponeEnterTransition()
    getPokemonList()
    showRv()
    viewModel.pokemonList.observe(viewLifecycleOwner) { pokemonResponse ->
      when (pokemonResponse) {
        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
        is Resource.Success -> {
          binding.progressBar.visibility = View.GONE
          adapter.setListPokemon(pokemonResponse.data)
          pokemonResponse.data?.results?.size?.let {
            adapter.notifyItemRangeInserted(adapter.itemCount,
              it
            )
          }
          (view.parent as ViewGroup).doOnPreDraw {
            startPostponedEnterTransition()
          }
        }
        is Resource.Error -> {
          binding.progressBar.visibility = View.GONE
          Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
        }
      }

    }
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.disposable.dispose()
  }

  private fun getPokemonList() {
    viewModel.listPokemon.observe(viewLifecycleOwner) { pokemonResponse ->
//      when (pokemonResponse) {
//        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
//        is Resource.Success -> {
//          binding.progressBar.visibility = View.GONE
//          adapter.setListPokemon(pokemonResponse.data)
//          adapter.notifyDataSetChanged()
//          (view?.parent as? ViewGroup)?.doOnPreDraw {
//            startPostponedEnterTransition()
//          }
//          showRv()
//        }
//        is Resource.Error -> {
//          binding.progressBar.visibility = View.GONE
//          Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
//        }
//      }
//    }
    }
  }

  private fun showRv() {
    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    binding.recyclerView.setHasFixedSize(true)
    binding.recyclerView.adapter = adapter
    binding.recyclerView.addOnScrollListener(object: EndlessRecyclerOnScrollListener(){
      override fun onLoadMore() {
        currentPage++
        viewModel.loadListPokemon(currentPage)
      }
    })
    adapter.clickListener = { data: ResultModel, imageView: ImageView ->
      val bundle = Bundle()
      val id = getIdFromUrl(data.url).toInt()
      bundle.putInt(Constant.EXTRA_POKEMON_ID, id)
      val extras =
        FragmentNavigatorExtras(imageView to requireContext().resources.getString(string.transition_to_detail))
      findNavController().navigate(
        R.id.detailPokemonFragment,
        bundle,
        null,
        extras
      )
    }
  }
}