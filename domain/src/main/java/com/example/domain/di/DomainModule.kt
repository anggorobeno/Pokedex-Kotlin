package com.example.domain.di

import com.example.domain.repository.IPokemonRepository
import com.example.domain.usecase.pokemon.PokemonInteractor
import com.example.domain.usecase.pokemon.PokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
  @Provides
  fun providesPokemonInteractor(repo: IPokemonRepository): PokemonUseCase {
    return PokemonInteractor(repo)
  }

}