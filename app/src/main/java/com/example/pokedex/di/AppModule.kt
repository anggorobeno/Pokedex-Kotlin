package com.example.pokedex.di;


import android.content.Context;

import com.example.pokedex.BuildConfig;
import com.example.pokedex.data.local.room.PokemonDB;
import com.example.pokedex.data.local.room.PokemonDao;
import com.example.pokedex.data.remote.network.ApiService;
import com.example.pokedex.utils.AppExecutors;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)

public class AppModule {
    public static final String BASE_URL = "https://pokeapi.co/";

    @Provides
    static String provideBaseUrl() {
        return BASE_URL;
    }

    @Provides
    static OkHttpClient provideOkHttpClient() {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();
        } else {
            return new OkHttpClient.Builder()
                    .build();
        }


    }

    @Provides
    static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    static PokemonDB provideDatabase(@ApplicationContext Context context){
        return PokemonDB.getInstance(context);
    }

    @Provides
    static PokemonDao provideDao(PokemonDB db){
        return db.pokemonDao();
    }

    @Provides
    static AppExecutors provideAppExecutors(){
        return new AppExecutors();
    }




}
