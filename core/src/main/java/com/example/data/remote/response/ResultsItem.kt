package com.example.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResultsItem (
  @SerializedName("name") val name: String? = null,
  @SerializedName("url") val url: String? = null,
)