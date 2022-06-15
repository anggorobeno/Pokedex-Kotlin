package com.example.di

import com.example.data.local.LocalDataSource
import com.example.data.local.room.PokemonDao
import com.example.data.remote.RemoteDataSource
import com.example.data.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
  @Provides
  fun provideLocalDataSource(dao: PokemonDao): LocalDataSource {
    return LocalDataSource(dao)
  }

  @Provides
  fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
    return RemoteDataSource(apiService)
  }
}