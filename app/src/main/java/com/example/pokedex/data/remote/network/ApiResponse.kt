package com.example.pokedex.data.remote.network

import com.example.pokedex.data.remote.network.StatusResponse
import com.example.pokedex.data.remote.network.ApiResponse
import com.example.pokedex.data.remote.network.StatusResponse.EMPTY
import com.example.pokedex.data.remote.network.StatusResponse.ERROR
import com.example.pokedex.data.remote.network.StatusResponse.SUCCESS

class ApiResponse<T>(val status: StatusResponse, val body: T?, val message: String?) {
  companion object {
    fun <T> success(body: T): ApiResponse<T> {
      return ApiResponse(SUCCESS, body, null)
    }

    fun <T> empty(msg: String, body: T?): ApiResponse<T> {
      return ApiResponse(EMPTY, body, msg)
    }

    fun <T> error(msg: String, body: T?): ApiResponse<T> {
      return ApiResponse(ERROR, body, msg)
    }
  }
}