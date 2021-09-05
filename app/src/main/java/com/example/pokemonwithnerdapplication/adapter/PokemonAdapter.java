package com.example.pokemonwithnerdapplication.adapter;

;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.pokemonwithnerdapplication.R;
import com.example.pokemonwithnerdapplication.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    List<Pokemon> pokemonList = new ArrayList<>();
    Context mcontext ;

    public PokemonAdapter(Context mcontext) {
        this.mcontext = mcontext;
    }

    public void setList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
        notifyDataSetChanged();
    }

    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        PokemonViewHolder postViewHolder = new PokemonViewHolder(v);

        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PokemonViewHolder holder, int position) {

        holder.textView.setText(pokemonList.get(position).getName());
        Glide.with(mcontext).load(pokemonList.get(position).getUrl())
                .into(holder.imageView);
    }

    public Pokemon getPokemonAt(int postion){
        return pokemonList.get(postion);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public PokemonViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pokemon_iv);
            textView = itemView.findViewById(R.id.pokemon_name_tv);

        }
    }
}
