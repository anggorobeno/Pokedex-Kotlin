package com.example.data

import android.util.Log
import javax.inject.Inject
import com.example.data.remote.RemoteDataSource
import com.example.data.local.LocalDataSource
import com.example.utils.AppExecutors
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.local.entity.PokemonEntity
import com.example.data.remote.response.PokemonResponse
import com.example.data.remote.network.ApiResponse
import com.example.data.local.entity.DetailPokemonEntity
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonModel
import com.example.data.remote.response.DetailPokemonResponse
import com.example.domain.repository.IPokemonRepository
import com.example.domain.utils.Resource
import java.util.ArrayList

open class PokemonRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
  private val appExecutors: AppExecutors
) : IPokemonRepository {
  override fun listPokemon(): LiveData<Resource<List<PokemonModel>>> =
    object : NetworkBoundResource<List<PokemonModel>, PokemonResponse>(
      appExecutors
    ) {
      override fun loadFromDB(): LiveData<List<PokemonModel>> {
        return Transformations.map(localDataSource.listPokemon) {
          it.map { item ->
            PokemonModel(
              item.id,
              item.isCaught,
              item.name,
              item.url
            )
          }
        }
      }

      override fun shouldFetch(data: List<PokemonModel>?): Boolean {
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

  override fun getDetailPokemon(id: Int): LiveData<Resource<DetailPokemonModel>> {
    return object : NetworkBoundResource<DetailPokemonModel, DetailPokemonResponse>(
      appExecutors
    ) {
      override fun loadFromDB(): LiveData<DetailPokemonModel> {
        return Transformations.map(localDataSource.getDetailPokemon(id)) {
          it?.isCaught?.let { it1 ->
            DetailPokemonModel(
              it1,
              it.id,
              it.name,
              it.height,
              it.weight,
              it.url,
              it.nickname,
            )
          }
        }
      }

      override fun shouldFetch(data: DetailPokemonModel?): Boolean {
        return data == null
      }

      override fun createCall(): LiveData<ApiResponse<DetailPokemonResponse>> {
        return remoteDataSource.getDetailPokemon(id)
      }

      override fun saveCallResult(data: DetailPokemonResponse?) {
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

  override val caughtPokemon: LiveData<List<DetailPokemonModel>>
    get() = Transformations.map(localDataSource.caughtPokemon) {
      it.map { item ->
        DetailPokemonModel(
          item.isCaught,
          item.id,
          item.name,
          item.height,
          item.weight,
          item.url,
          item.nickname
        )
      }
    }

  override fun setCaughtPokemon(
    pokemonEntity: DetailPokemonModel,
    state: Boolean,
    nickname: String
  ) {
    val entity = DetailPokemonEntity(
      pokemonEntity.isCaught,
      pokemonEntity.id,
      pokemonEntity.name,
      pokemonEntity.height,
      pokemonEntity.weight,
      pokemonEntity.url,
      pokemonEntity.nickname,
    )
    appExecutors.diskIO()
      .execute { localDataSource.setCaughtPokemon(entity, state, nickname) }
  }
}