package com.example.data.remote.network

import retrofit2.http.GET
import com.example.data.remote.response.PokemonResponse
import com.example.data.remote.response.DetailPokemonResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
  @GET("api/v2/pokemon") fun getPokemonList(
    @Query("offset") offset: Int,
    @Query("limit") limit: Int
  ): Call<PokemonResponse>

  @GET("api/v2/pokemon/{id}")
  fun getDetailPokemon(@Path("id") id: Int): Call<DetailPokemonResponse>

  @GET("api/v2/pokemon") fun getPokemonListPaging(
    @Query("offset") offset: Int,
    @Query("limit") limit: Int
  ): Single<PokemonResponse>

  @GET("api/v2/pokemon") fun getPokemonListObservable(
    @Query("offset") offset: Int,
    @Query("limit") limit: Int
  ): Observable<PokemonResponse>
}