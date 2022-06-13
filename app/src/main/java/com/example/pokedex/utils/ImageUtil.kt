package com.example.pokedex.utils

import android.R.attr
import android.R.color
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

object ImageUtil {
  fun generateBackgroundPalette(
    context: Context,
    drawable: String,
    imageSource: ImageView,
    targetSource: ImageView
  ) {
    Glide.with(context)
      .asBitmap()
      .load(drawable)
      .into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
          imageSource.setImageBitmap(resource)
          val palette = Palette.from(resource).generate().vibrantSwatch
          palette?.rgb?.let {
            targetSource.setColorFilter(it)
          }
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }
      })

  }
}