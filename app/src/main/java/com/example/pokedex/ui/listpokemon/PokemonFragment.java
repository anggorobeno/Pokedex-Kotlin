package com.example.pokedex.ui.listpokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.pokedex.R;
import com.example.pokedex.data.local.entity.PokemonEntity;
import com.example.pokedex.databinding.FragmentPokemonBinding;
import com.example.pokedex.ui.adapter.PokemonAdapter;
import com.example.pokedex.utils.Constant;
import com.example.pokedex.utils.Helper;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PokemonFragment extends Fragment {
    private PokemonViewModel viewModel;
    private FragmentPokemonBinding binding;
    private PokemonAdapter adapter = new PokemonAdapter();

    public PokemonFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        binding = FragmentPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPokemonList();
    }

    private void showRv() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickCallback(new PokemonAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(PokemonEntity data) {
                Bundle bundle = new Bundle();
                int id = Integer.parseInt(Helper.getIdFromUrl(data.getUrl()));
                bundle.putInt(Constant.EXTRA_POKEMON_ID, id);
                Navigation.findNavController(requireView()).navigate(R.id.detailPokemonFragment, bundle);
            }
        });
    }

    private void getPokemonList() {
        viewModel.getListPokemon().observe(getViewLifecycleOwner(), pokemonResponse -> {
            if (pokemonResponse != null) {
                switch (pokemonResponse.status) {
                    case LOADING:
                        binding.progressBar.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        binding.progressBar.setVisibility(View.GONE);
                        adapter.setListPokemon(pokemonResponse.data);
                        adapter.notifyDataSetChanged();
                        showRv();
                        break;
                    case ERROR:
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


}