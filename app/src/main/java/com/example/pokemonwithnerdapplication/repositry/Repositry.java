package com.example.pokemonwithnerdapplication.repositry;

import androidx.lifecycle.LiveData;

import com.example.pokemonwithnerdapplication.db.PokemonDao;
import com.example.pokemonwithnerdapplication.model.Pokemon;
import com.example.pokemonwithnerdapplication.model.PokemonResponse;
import com.example.pokemonwithnerdapplication.network.PokemonApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


public class Repositry {

    private PokemonApiService pokemonApiService;
    private PokemonDao pokemonDao;

    @Inject
    public Repositry(PokemonApiService pokemonApiService,PokemonDao pokemonDao) {
        this.pokemonApiService = pokemonApiService;
        this.pokemonDao = pokemonDao;
    }

    public Observable<PokemonResponse> getPokemons(){
        return pokemonApiService.getPokemon();

    }

    public void insertPokemon(Pokemon pokemon){
        pokemonDao.insertPokemon(pokemon);
    }


    public void deletePokemon(String pokemonName){
        pokemonDao.deletePokemon(pokemonName);
    }


    public LiveData<List<Pokemon>> getFav(){
        return pokemonDao.getPokemons();
    }




}
