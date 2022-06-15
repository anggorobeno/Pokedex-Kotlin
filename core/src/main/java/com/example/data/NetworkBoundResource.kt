package com.example.data

import com.example.utils.AppExecutors
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.LiveData
import com.example.data.remote.network.ApiResponse
import com.example.domain.utils.Resource

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
    ) { newData: ResultType -> result.setValue(Resource.Loading(newData)) }
    result.addSource(apiResponse) { response: ApiResponse<RequestType> ->
      result.removeSource(apiResponse)
      result.removeSource(dbSource)
      when (response) {
        is ApiResponse.Success -> mExecutors.diskIO().execute {
          saveCallResult(response.body)
          mExecutors.mainThread().execute {
            result.addSource(
              loadFromDB()
            ) { newData: ResultType -> result.setValue(Resource.Success(newData)) }
          }
        }
        is ApiResponse.Empty -> mExecutors.mainThread().execute {
          result.addSource(
            loadFromDB()
          ) { newData: ResultType -> result.setValue(Resource.Success(newData)) }
        }
        is ApiResponse.Error -> {
          onFetchFailed()
          result.addSource(dbSource) { newData: ResultType ->
            result.setValue(
              response.message?.let {
                Resource.Error(
                  it,
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
    result.value = Resource.Loading(null)
    val dbSource = loadFromDB()
    result.addSource(dbSource) { data: ResultType ->
      result.removeSource(dbSource)
      if (shouldFetch(data)) {
        fetchFromNetwork(dbSource)
      } else {
        result.addSource(dbSource) { newData: ResultType -> result.setValue(Resource.Success(newData)) }
      }
    }
  }
}