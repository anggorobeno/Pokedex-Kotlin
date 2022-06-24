package com.example.pokedex.utils

import android.R.attr
import android.R.color
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.skydoves.rainbow.rainbow
import com.skydoves.whatif.whatIf
import com.skydoves.whatif.whatIfNotNull
import timber.log.Timber
import timber.log.Timber.Forest

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

  fun bindImageWithPalette(
    context: Context,fragment:Fragment, url: String, targetView: ImageView, targetBackground: View
  ) {
    Glide.with(context)
      .load(url)
      .listener(
        GlidePalette.with(url)
          .use(BitmapPalette.Profile.MUTED_LIGHT)
          .intoCallBack { palette ->
            val rgb = palette?.dominantSwatch?.rgb
            if (rgb != null && rgb != -15724528) {
              targetBackground.setBackgroundColor(rgb)
              Timber.d(context.toString())
              if (context is AppCompatActivity) {
                context.window.apply {
                  addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                  statusBarColor = rgb
                }
              }
            }
            fragment.startPostponedEnterTransition()

          }.crossfade(true)
      ).into(targetView)
  }

  fun bindImageWithPalette(
    context: Context, url: String, targetView: ImageView, cardView: MaterialCardView
  ) {
    Glide.with(context)
      .load(url)
      .listener(
        GlidePalette.with(url)
          .use(BitmapPalette.Profile.MUTED_LIGHT)
          .intoCallBack { palette ->
            val rgb = palette?.dominantSwatch?.rgb
            if (rgb != null && rgb != -15724528) {
              cardView.setCardBackgroundColor(rgb)
            }
          }.crossfade(true)
      ).into(targetView)
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
            Timber.d(it.toString())
            targetSource.setColorFilter(it)
          }
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }
      })

  }
}
