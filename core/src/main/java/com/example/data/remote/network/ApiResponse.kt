package com.example.data.remote.network

sealed class ApiResponse<out T>(val body: T?, val message: String?) {
  class Success<T>(body: T) : ApiResponse<T>(body, null)
  class Empty(message: String?) : ApiResponse<Nothing>(null, message)
  class Error(message: String?) : ApiResponse<Nothing>(null, message)
}