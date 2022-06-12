package com.example.pokedex.ui.detailpokemon;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pokedex.R;
import com.example.pokedex.databinding.FragmentDetailPokemonBinding;
import com.example.pokedex.utils.BaseDialog;
import com.example.pokedex.utils.Constant;
import com.example.pokedex.utils.Helper;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailPokemonFragment extends Fragment {

  private FragmentDetailPokemonBinding binding;
  private DetailPokemonViewModel viewModel;
  private BaseDialog dialog;

  public DetailPokemonFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    viewModel = new ViewModelProvider(this).get(DetailPokemonViewModel.class);
    binding = FragmentDetailPokemonBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    int id = requireArguments().getInt(Constant.EXTRA_POKEMON_ID);
    Log.d(TAG, "onViewCreated: " + id);
    showDetailPokemon(id);
    binding.icBack.setOnClickListener(view1 -> {
      Navigation.findNavController(requireView()).navigateUp();
    });
  }

  private void caughtPokemon() {
    viewModel.getDetailPokemon().observe(getViewLifecycleOwner(), detailPokemonEntityResource -> {
      if (detailPokemonEntityResource != null) {
        switch (detailPokemonEntityResource.status) {
          case SUCCESS:
            if (detailPokemonEntityResource.data != null) {
              boolean state = detailPokemonEntityResource.data.isCaught();
              if (state) {
                binding.tvCatchPokemon.setText("Release!");
              } else {
                binding.tvCatchPokemon.setText("Catch!");
              }
              Log.d(TAG, "caughtPokemon: " + detailPokemonEntityResource.data.isCaught());
              binding.ivCatch.setOnClickListener(view -> {
                Random random = new Random();
                int randomNumber = random.nextInt(3 - 1) + 1;
                if (!state) {
                  if (randomNumber == 1) {
                    dialog = new BaseDialog(requireContext(), R.layout.fragment_bottom_dialog);
                    dialog.show();
                    dialog.updateText(new BaseDialog.DialogCallback() {
                      @Override public void onDialogShow(@NonNull String text) {
                        viewModel.setCaughtPokemon(text);
                        dialog.dismiss();
                        Snackbar.make(view, text + " has been added to pokemon list",
                            Snackbar.LENGTH_SHORT).show();
                      }
                    });
                  } else {
                    Snackbar.make(view, "You Failed to Catch a Pokemon", Snackbar.LENGTH_SHORT)
                        .show();
                  }
                } else {
                  Snackbar.make(view, "You Released a Pokemon", Snackbar.LENGTH_SHORT).show();
                  viewModel.setCaughtPokemon(" ");
                }
              });
            }
        }
      }
    });
  }

  private void showDetailPokemon(int id) {
    viewModel.setPokemonId(id);
    viewModel.getDetailPokemon().observe(getViewLifecycleOwner(), detailPokemonResponse -> {
      if (detailPokemonResponse != null) {
        switch (detailPokemonResponse.status) {
          case LOADING:
            binding.progressBar.setVisibility(View.VISIBLE);
            break;
          case SUCCESS:
            binding.progressBar.setVisibility(View.GONE);
            Glide.with(requireContext())
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                    + id
                    + ".png")
                .into(binding.ivPokemon);
            binding.tvPokemonName.setText(detailPokemonResponse.data.getName());
            binding.tvPokemonHeight.setText(
                Helper.getHeight(detailPokemonResponse.data.getHeight()));
            binding.tvPokemonWeight.setText(
                Helper.getWeight(detailPokemonResponse.data.getWeight()));
            binding.tvPokemonId.setText(Helper.idConverter(id));
            break;
          case ERROR:
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();
        }
      }
    });
    caughtPokemon();
  }
}