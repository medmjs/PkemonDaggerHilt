package com.example.pokemonwithnerdapplication.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pokemonwithnerdapplication.db.PokemonDB;
import com.example.pokemonwithnerdapplication.db.PokemonDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public static PokemonDB provideDB(Application application) {
        return Room.databaseBuilder(application, PokemonDB.class, "pokemon_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static PokemonDao provideDao(PokemonDB pokemonDB) {
        return pokemonDB.pokemonDao();
    }
}
