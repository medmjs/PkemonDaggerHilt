package com.example.pokemonwithnerdapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private PokemonViewModel viewModel;
    private PokemonAdapter pokemonAdapter;
    private RecyclerView recyclerView;
    private Button fav_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_rv);
        fav_btn =  findViewById(R.id.main_btn);

        pokemonAdapter =new PokemonAdapter(this);
        recyclerView.setAdapter(pokemonAdapter);

        // need search
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        viewModel.getPokemon();

        viewModel.getPokemonMutableLiveData().observe(this, new Observer<ArrayList<Pokemon>>() {
            @Override
            public void onChanged(ArrayList<Pokemon> pokemons) {
                pokemonAdapter.setList(pokemons);
            }
        });

        setupSwipe();
        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getBaseContext(),FavActivity.class);
                startActivity(intent);
            }
        });
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

                viewModel.insertPokemon(pokemon);
                pokemonAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Insert Pokemon to DB", Toast.LENGTH_SHORT).show();


            }

        };
        ItemTouchHelper itemTouchHelper =new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
}