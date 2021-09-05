package com.example.pokemonwithnerdapplication.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pokemonwithnerdapplication.model.Pokemon;
import com.example.pokemonwithnerdapplication.model.PokemonResponse;
import com.example.pokemonwithnerdapplication.repositry.Repositry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PokemonViewModel extends ViewModel {

    public Repositry repositry;
    MutableLiveData<ArrayList<Pokemon>> pokemonMutableLiveData = new MutableLiveData<>();
    LiveData<List<Pokemon>> pokemonLiveData = null;

    @ViewModelInject
    public PokemonViewModel(Repositry repositry) {
        this.repositry = repositry;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonMutableLiveData() {
        return pokemonMutableLiveData;
    }

    @SuppressLint("CheckResult")
    public void getPokemon() {
        repositry.getPokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Exception {

                        ArrayList<Pokemon> pokemons = pokemonResponse.getResults();
                        for (Pokemon pokemon : pokemons) {
                            String[] urlSplit = pokemon.getUrl().split("/");
                            String number = urlSplit[urlSplit.length - 1];
                            String newurl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + number + ".png";
                            pokemon.setUrl(newurl);
                        }
                        return pokemons;
                    }


                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemonMutableLiveData.setValue(result),
                        error -> Log.e("ViewModel", error.getMessage()));
    }


    public void insertPokemon(Pokemon pokemon) {
        repositry.insertPokemon(pokemon);
    }


    public void deletePokemon(String pokemonName) {
        repositry.deletePokemon(pokemonName);
    }


    public void getFav() {
        pokemonLiveData = repositry.getFav();
    }

    public LiveData<List<Pokemon>> getPokemonLiveData() {
        return pokemonLiveData;
    }
}
