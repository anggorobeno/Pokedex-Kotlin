package com.example.pokedex.ui.detailpokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.pokedex.R.layout
import com.example.pokedex.data.local.entity.DetailPokemonEntity
import com.example.pokedex.databinding.FragmentDetailPokemonBinding
import com.example.pokedex.utils.BaseDialog
import com.example.pokedex.utils.BaseDialog.DialogCallback
import com.example.pokedex.utils.Constant
import com.example.pokedex.utils.Helper.getHeight
import com.example.pokedex.utils.Helper.getWeight
import com.example.pokedex.utils.Helper.idConverter
import com.example.pokedex.utils.Resource
import com.example.pokedex.utils.Status.ERROR
import com.example.pokedex.utils.Status.LOADING
import com.example.pokedex.utils.Status.SUCCESS
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random

@AndroidEntryPoint
class DetailPokemonFragment : Fragment() {
  private var _binding: FragmentDetailPokemonBinding? = null
  private val binding get() = _binding!!
  lateinit var viewModel: DetailPokemonViewModel
  private var dialog: BaseDialog? = null
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    viewModel = ViewModelProvider(this).get(DetailPokemonViewModel::class.java)
    _binding = FragmentDetailPokemonBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val id = requireArguments().getInt(Constant.EXTRA_POKEMON_ID)
    showDetailPokemon(id)
    binding.icBack.setOnClickListener {
      Navigation.findNavController(requireView()).navigateUp()
    }
  }

  private fun caughtPokemon() {
    viewModel.detailPokemon.observe(viewLifecycleOwner) { detailPokemonEntityResource: Resource<DetailPokemonEntity> ->
      when (detailPokemonEntityResource.status) {
        SUCCESS -> if (detailPokemonEntityResource.data != null) {
          val state = detailPokemonEntityResource.data.isCaught
          if (state) {
            binding.tvCatchPokemon.text = "Release!"
          } else {
            binding.tvCatchPokemon.text = "Catch!"
          }
          var name = if (detailPokemonEntityResource.data.nickname == null
            || detailPokemonEntityResource.data.nickname == ""
          ) detailPokemonEntityResource.data.name else detailPokemonEntityResource.data.nickname!!
          binding.ivCatch.setOnClickListener {

            val randomCatch = viewModel.getRandomLogic
            if (!state) {
              if (randomCatch == 1) {
                dialog = BaseDialog(requireContext(), layout.fragment_bottom_dialog)
                dialog?.show()
                dialog?.setNickname(object : DialogCallback {
                  override fun onDialogShow(text: String) {
                    dialog?.dismiss()
                    viewModel.setCaughtPokemon(text)
                    Snackbar.make(
                      requireView(), "$name has been added to pokemon list",
                      Snackbar.LENGTH_SHORT
                    ).show()
                    return
                  }
                })

              } else {
                Snackbar.make(requireView(), "You Failed to Catch a Pokemon", Snackbar.LENGTH_SHORT)
                  .show()
              }
            } else {
              viewModel.setCaughtPokemon("")
              Snackbar.make(requireView(), "$name has been released", Snackbar.LENGTH_SHORT).show()

            }
          }
        }
      }
    }
  }

  private fun showDetailPokemon(id: Int) {
    viewModel.setPokemonId(id)
    viewModel.detailPokemon.observe(viewLifecycleOwner) { detailPokemonResponse: Resource<DetailPokemonEntity>? ->
      if (detailPokemonResponse != null) {
        when (detailPokemonResponse.status) {
          LOADING -> binding.progressBar.visibility = View.VISIBLE
          SUCCESS -> {
            binding.progressBar.visibility = View.GONE
            Glide.with(requireContext())
              .load(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                    + id
                    + ".png"
              )
              .into(binding.ivPokemon)
            val name = if (detailPokemonResponse.data!!.nickname == null
              || detailPokemonResponse.data.nickname == ""
            ) detailPokemonResponse.data.name else detailPokemonResponse.data.nickname!!
            binding.tvPokemonName.text = name
            binding.tvPokemonHeight.text = getHeight(
              detailPokemonResponse.data.height.toDouble()
            )
            binding.tvPokemonWeight.text = getWeight(
              detailPokemonResponse.data.weight.toDouble()
            )
            binding.tvPokemonId.text = idConverter(id)
          }
          ERROR -> {
            binding.progressBar.visibility = View.GONE
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
    caughtPokemon()
  }
}