package com.example.pokedex.ui.adapter;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex.data.local.entity.PokemonEntity;
import com.example.pokedex.databinding.PokemonListBinding;
import com.example.pokedex.utils.Helper;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {
    private ArrayList<PokemonEntity> listPokemon = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
    public void setListPokemon(List<PokemonEntity> listPokemon){
        Log.d(TAG, "setListPokemon: " + String.valueOf(listPokemon));
        if (listPokemon == null) return;
        this.listPokemon.clear();
        this.listPokemon.addAll(listPokemon);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokemonListBinding binding = PokemonListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listPokemon.get(position));
        holder.itemView.setOnClickListener(view -> {onItemClickCallback.onItemClicked(listPokemon.get(holder.getBindingAdapterPosition()));
        });

    }

    @Override
    public int getItemCount() {
        return listPokemon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private PokemonListBinding binding;

        public ViewHolder(@NonNull PokemonListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        private void bind(PokemonEntity pokemon){
            String id = Helper.getIdFromUrl(pokemon.getUrl());
            binding.tvPokemonName.setText(pokemon.getName());
            Glide.with(itemView)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+id+".png")
                    .into(binding.ivPokemon);
        }

    }
    public interface OnItemClickCallback {
        void onItemClicked(PokemonEntity data);
    }
}
