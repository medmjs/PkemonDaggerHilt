package com.example.pokemonwithnerdapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pokemonwithnerdapplication.adapter.PokemonAdapter;
import com.example.pokemonwithnerdapplication.model.Pokemon;
import com.example.pokemonwithnerdapplication.viewmodels.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavActivity extends AppCompatActivity {


    private PokemonViewModel viewModel;
    private PokemonAdapter pokemonAdapter;
    private RecyclerView recyclerView;
    private Button main_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerView =findViewById(R.id.fav_rv);
        main_btn = findViewById(R.id.fav_btn);

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),MainActivity.class));
            }
        });


        pokemonAdapter =new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);



        viewModel =new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getFav();

        viewModel.getPokemonLiveData().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                pokemonAdapter.setList(pokemons);
            }
        });


        setupSwipe();

    }


    private void setupSwipe(){

        ItemTouchHelper.SimpleCallback callback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int swipePostion = viewHolder.getAdapterPosition();
                Pokemon pokemon = pokemonAdapter.getPokemonAt(swipePostion);

                viewModel.deletePokemon(pokemon.getName());
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(FavActivity.this, "delete Pokemon from  DB", Toast.LENGTH_SHORT).show();


            }

        };
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

}