package com.example.pokedex.utils

import androidx.databinding.BindingAdapter
import com.skydoves.progressview.ProgressView

class ViewBinding {
  companion object {
    @BindingAdapter("progressValue")
    @JvmStatic
    fun setProgressViewValue(progressView: ProgressView, value: Float) {
      progressView.progress = value
    }
  }

}