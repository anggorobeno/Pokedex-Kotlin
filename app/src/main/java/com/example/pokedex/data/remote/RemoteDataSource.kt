package com.example.pokedex.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.data.remote.network.ApiResponse
import com.example.pokedex.data.remote.network.ApiService
import com.example.pokedex.data.remote.response.DetailPokemonResponse
import com.example.pokedex.data.remote.response.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
  val listPokemon: LiveData<ApiResponse<PokemonResponse>>
    get() {
      val result = MutableLiveData<ApiResponse<PokemonResponse>>()
      apiService.getPokemonList(20, 10).enqueue(object : Callback<PokemonResponse> {
        override fun onResponse(
          call: Call<PokemonResponse>,
          response: Response<PokemonResponse>
        ) {
          val responseBody = response.body()
          if (response.isSuccessful && responseBody != null) {
            result.value = ApiResponse.Success(responseBody)
          } else result.value = ApiResponse.Empty("Data is empty")
        }

        override fun onFailure(call: Call<PokemonResponse?>, t: Throwable) {
          result.value = t.message?.let { ApiResponse.Error(it) }
        }
      })
      return result
    }

  fun getDetailPokemon(id: Int): LiveData<ApiResponse<DetailPokemonResponse>> {
    val result = MutableLiveData<ApiResponse<DetailPokemonResponse>>()
    apiService.getDetailPokemon(id).enqueue(object : Callback<DetailPokemonResponse> {
      override fun onResponse(
        call: Call<DetailPokemonResponse>,
        response: Response<DetailPokemonResponse>
      ) {
        val responseBody = response.body()
        if (responseBody != null && response.isSuccessful) {
          result.value = ApiResponse.Success(responseBody)
        } else result.setValue(ApiResponse.Empty(response.message()));
      }

      override fun onFailure(call: Call<DetailPokemonResponse?>, t: Throwable) {
        result.value = t.message?.let { ApiResponse.Error(it) }
      }
    })
    return result
  }
}