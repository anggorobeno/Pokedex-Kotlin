package com.example.di

import com.example.data.PokemonRepository
import com.example.data.local.LocalDataSource
import com.example.data.remote.RemoteDataSource
import com.example.domain.repository.IPokemonRepository
import com.example.utils.AppExecutors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

  @Provides
  fun provideRepository(
    localDataSource: LocalDataSource,
    remoteDataSource: RemoteDataSource,
    appExecutors: AppExecutors
  ): IPokemonRepository {
    return PokemonRepository(remoteDataSource, localDataSource, appExecutors)
  }
}