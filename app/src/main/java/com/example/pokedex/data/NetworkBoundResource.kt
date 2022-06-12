package com.example.pokedex.data

import com.example.pokedex.utils.AppExecutors
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.LiveData
import com.example.pokedex.data.remote.network.ApiResponse
import com.example.pokedex.data.remote.network.StatusResponse
import com.example.pokedex.data.remote.network.StatusResponse.EMPTY
import com.example.pokedex.data.remote.network.StatusResponse.ERROR
import com.example.pokedex.data.remote.network.StatusResponse.SUCCESS
import com.example.pokedex.utils.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {
  private val result = MediatorLiveData<Resource<ResultType>>()
  protected fun onFetchFailed() {}
  protected abstract fun loadFromDB(): LiveData<ResultType>
  protected abstract fun shouldFetch(data: ResultType?): Boolean
  protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
  protected abstract fun saveCallResult(data: RequestType?)
  private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
    val apiResponse = createCall()
    result.addSource(
      dbSource
    ) { newData: ResultType -> result.setValue(Resource.loading(newData)) }
    result.addSource(apiResponse) { response: ApiResponse<RequestType> ->
      result.removeSource(apiResponse)
      result.removeSource(dbSource)
      when (response.status) {
        SUCCESS -> mExecutors.diskIO().execute {
          saveCallResult(response.body)
          mExecutors.mainThread().execute {
            result.addSource(
              loadFromDB()
            ) { newData: ResultType -> result.setValue(Resource.success(newData)) }
          }
        }
        EMPTY -> mExecutors.mainThread().execute {
          result.addSource(
            loadFromDB()
          ) { newData: ResultType -> result.setValue(Resource.success(newData)) }
        }
        ERROR -> {
          onFetchFailed()
          result.addSource(dbSource) { newData: ResultType ->
            result.setValue(
              response.message?.let {
                Resource.error(
                  it,
                  newData
                )
              }
            )
          }
        }
      }
    }
  }

  fun asLiveData(): LiveData<Resource<ResultType>> {
    return result
  }

  init {
    result.value = Resource.loading(null)
    val dbSource = loadFromDB()
    result.addSource(dbSource) { data: ResultType ->
      result.removeSource(dbSource)
      if (shouldFetch(data)) {
        fetchFromNetwork(dbSource)
      } else {
        result.addSource(dbSource) { newData: ResultType -> result.setValue(Resource.success(newData)) }
      }
    }
  }
}