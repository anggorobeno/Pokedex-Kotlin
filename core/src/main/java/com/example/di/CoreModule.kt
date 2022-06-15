package com.example.di

import android.content.Context
import com.example.core.BuildConfig
import com.example.data.PokemonRepository
import com.example.data.local.room.PokemonDB.Companion.getInstance
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.data.remote.network.ApiService
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.data.local.room.PokemonDB
import com.example.data.local.room.PokemonDao
import com.example.domain.repository.IPokemonRepository
import com.example.utils.AppExecutors
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit.Builder

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
  private const val BASE_URL = "https://pokeapi.co/"
   @Provides fun provideBaseUrl(): String {
    return BASE_URL
  }

   @Provides fun provideOkHttpClient(): OkHttpClient {
    return if (BuildConfig.DEBUG) {
      val httpLoggingInterceptor = HttpLoggingInterceptor()
      httpLoggingInterceptor.setLevel(BODY)
      OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
    } else {
      OkHttpClient.Builder()
        .build()
    }
  }

   @Provides fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(client)
      .build()
  }

   @Provides fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

   @Provides fun provideDatabase(@ApplicationContext context: Context): PokemonDB {
    return getInstance(context)
  }

   @Provides fun provideDao(db: PokemonDB): PokemonDao {
    return db.pokemonDao()
  }

  @Provides fun provideAppExecutors(): AppExecutors {
    return AppExecutors()
  }


}