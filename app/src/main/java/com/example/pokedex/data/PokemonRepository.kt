package com.example.pokedex.data

import javax.inject.Inject
import com.example.pokedex.data.remote.RemoteDataSource
import com.example.pokedex.data.local.LocalDataSource
import com.example.pokedex.utils.AppExecutors
import androidx.lifecycle.LiveData
import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.data.remote.response.PokemonResponse
import com.example.pokedex.data.remote.network.ApiResponse
import com.example.pokedex.data.local.entity.DetailPokemonEntity
import com.example.pokedex.data.remote.response.DetailPokemonResponse
import android.content.ContentValues
import android.util.Log
import com.example.pokedex.utils.Resource
import java.util.ArrayList

open class PokemonRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
  private val appExecutors: AppExecutors
) : IPokemonRepository {
  override fun listPokemon(): LiveData<Resource<List<PokemonEntity>>> =
    object : NetworkBoundResource<List<PokemonEntity>, PokemonResponse>(
      appExecutors
    ) {
      override fun loadFromDB(): LiveData<List<PokemonEntity>> {
        return localDataSource.listPokemon
      }

      override fun shouldFetch(data: List<PokemonEntity>?): Boolean {
        return data!!.isEmpty()
      }

      override fun createCall(): LiveData<ApiResponse<PokemonResponse>> {
        return remoteDataSource.listPokemon
      }

      override fun saveCallResult(data: PokemonResponse?) {
        val pokemonList = ArrayList<PokemonEntity>()
        for ((name, url) in data?.results!!) {
          val pokemon = PokemonEntity(
            0,
            false,
            name!!,
            url!!
          )
          pokemonList.add(pokemon)
        }
        localDataSource.insertPokemon(pokemonList)
      }
    }.asLiveData()

  override fun getDetailPokemon(id: Int): LiveData<Resource<DetailPokemonEntity>> {
    return object : NetworkBoundResource<DetailPokemonEntity, DetailPokemonResponse>(
      appExecutors
    ) {
      override fun loadFromDB(): LiveData<DetailPokemonEntity> {
        return localDataSource.getDetailPokemon(id)
      }

      override fun shouldFetch(data: DetailPokemonEntity?): Boolean {
        return data == null
      }

      override fun createCall(): LiveData<ApiResponse<DetailPokemonResponse>> {
        return remoteDataSource.getDetailPokemon(id)
      }

      override fun saveCallResult(data: DetailPokemonResponse?) {
        Log.d("TAG", "saveCallResult: $data")
        val pokemonEntities: MutableList<DetailPokemonEntity> = ArrayList()
        val pokemon = DetailPokemonEntity(
          false,
          data!!.id!!,
          data.name,
          data.height,
          data.weight,
          data.url,
        )
        pokemonEntities.add(pokemon)
        localDataSource.insertPokemonDetail(pokemonEntities)
      }
    }.asLiveData()
  }

  override val caughtPokemon: LiveData<List<DetailPokemonEntity>>
    get() = localDataSource.caughtPokemon

  override fun setCaughtPokemon(
    pokemonEntity: DetailPokemonEntity, state: Boolean,
    nickname: String
  ) {
    appExecutors.diskIO()
      .execute { localDataSource.setCaughtPokemon(pokemonEntity, state, nickname) }
  }
}