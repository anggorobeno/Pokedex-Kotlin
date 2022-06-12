package com.example.pokedex.data.remote

import javax.inject.Inject
import com.example.pokedex.data.remote.network.ApiService
import androidx.lifecycle.LiveData
import com.example.pokedex.data.remote.network.ApiResponse
import com.example.pokedex.data.remote.response.PokemonResponse
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.data.remote.response.DetailPokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            result.value = ApiResponse.success(responseBody)
          }
        }

        override fun onFailure(call: Call<PokemonResponse?>, t: Throwable) {}
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
          result.value = ApiResponse.success(responseBody)
        }
        //                else result.setValue(ApiResponse.error(response.message(),null));
      }

      override fun onFailure(call: Call<DetailPokemonResponse?>, t: Throwable) {
//                result.setValue(ApiResponse);
      }
    })
    return result
  }
}