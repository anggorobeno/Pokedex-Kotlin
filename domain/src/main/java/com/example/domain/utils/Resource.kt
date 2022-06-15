package com.example.domain.utils

sealed class Resource<out T>(val data: T?, val message: String?) {
  class Loading<T>(data: T?) : Resource<T>(data, null)
  class Success<T>(data: T) : Resource<T>(data, null)
  class Error(message: String?) : Resource<Nothing>(null, message)

}