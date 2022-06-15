package com.example.pokedex.data.remote.network

import com.example.pokedex.data.remote.network.StatusResponse
import com.example.pokedex.data.remote.network.ApiResponse
import com.example.pokedex.data.remote.network.StatusResponse.EMPTY
import com.example.pokedex.data.remote.network.StatusResponse.ERROR
import com.example.pokedex.data.remote.network.StatusResponse.SUCCESS

sealed class ApiResponse<out T>(val body: T?, val message: String?) {
  class Success<T>(body: T) : ApiResponse<T>(body, null)
  class Empty(message: String?) : ApiResponse<Nothing>(null, message)
  class Error(message: String?) : ApiResponse<Nothing>(null, message)
}