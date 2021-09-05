package com.example.pokemonwithnerdapplication.network;

import com.example.pokemonwithnerdapplication.model.PokemonResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokemonApiService {
    @GET("pokemon")
    public Observable<PokemonResponse> getPokemon();
}
