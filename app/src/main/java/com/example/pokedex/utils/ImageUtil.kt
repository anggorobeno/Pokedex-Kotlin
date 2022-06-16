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
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.pokedex.R
import com.example.pokedex.ui.detailpokemon.DetailPokemonFragment
import com.skydoves.rainbow.rainbow
import com.skydoves.whatif.whatIf
import com.skydoves.whatif.whatIfNotNull

object ImageUtil {
  fun generateBackgroundPalette(
    context: Context,
    fragment: Fragment,
    drawable: String,
    imageSource: ImageView,
    targetSource: View
  ) {
    Glide.with(context)
      .asBitmap()
      .load(drawable)
      .into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
          fragment.startPostponedEnterTransition()
          imageSource.setImageBitmap(resource)
          val palette = Palette.from(resource).generate().dominantSwatch
          palette.whatIfNotNull {
            targetSource.setBackgroundColor(it.rgb)
          }
        }

        override fun onLoadCleared(placeholder: Drawable?) {
          fragment.startPostponedEnterTransition()

        }
      })
  }

  fun generateImageBackgroundPalette(
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
          val palette = Palette.from(resource).generate().dominantSwatch
          palette?.rgb?.let {
            targetSource.setColorFilter(it)
          }
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }
      })

  }
}