package com.example.pokedex

import dagger.hilt.android.HiltAndroidApp
import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MyApp : Application(){
  override fun onCreate() {
    super.onCreate()
    Timber.plant(DebugTree())
  }

}