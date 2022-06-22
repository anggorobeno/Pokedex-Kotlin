package com.example.data

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.data.local.LocalDataSource
import com.example.data.local.entity.DetailPokemonEntity
import com.example.data.local.entity.PokemonEntity
import com.example.data.remote.RemoteDataSource
import com.example.data.remote.network.ApiResponse
import com.example.data.remote.response.DetailPokemonResponse
import com.example.data.remote.response.PokemonResponse
import com.example.domain.model.DetailPokemonModel
import com.example.domain.model.PokemonModel
import com.example.domain.model.ResultModel
import com.example.domain.repository.IPokemonRepository
import com.example.domain.utils.Resource
import com.example.utils.AppExecutors
import io.reactivex.BackpressureStrategy.BUFFER
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

open class PokemonRepository @Inject constructor(
  private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
  private val appExecutors: AppExecutors
) : IPokemonRepository {
  @SuppressLint("CheckResult")
  override fun getListPokemonObservable(): Flowable<Resource<PokemonModel>> {
    val subject = BehaviorSubject.createDefault<Resource<PokemonModel>>(Resource.Loading(null))
    remoteDataSource.getListPokemonObservable()
      .map {
        PokemonResponse.transform(it)
      }
      .subscribe({ value ->
        subject.onNext(Resource.Success(value))
      }, { error ->
        subject.onNext(Resource.Error(error.localizedMessage))
      })
    return subject.toFlowable(BUFFER)
  }

  override fun listPokemon(): LiveData<Resource<PokemonModel>> =
    object : NetworkBoundResource<PokemonModel, PokemonResponse>(
      appExecutors
    ) {
      override fun loadFromDB(): LiveData<PokemonModel> {
        return Transformations.map(localDataSource.listPokemon) { item ->
          val transformedList = arrayListOf<ResultModel>()
          item.forEach {
            transformedList.add(PokemonEntity.transform(it))
          }
          Timber.d(message = "a $transformedList")
          PokemonModel(
            null,
            null,
            0,
            transformedList
          )
        }
      }

      override fun shouldFetch(data: PokemonModel?): Boolean {
        return data!!.results.isEmpty()
      }

      override fun createCall(): LiveData<ApiResponse<PokemonResponse>> {
        return remoteDataSource.listPokemon
      }

      override fun saveCallResult(data: PokemonResponse?) {
        val pokemonList = ArrayList<PokemonEntity>()
        Timber.d(data.toString())
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



