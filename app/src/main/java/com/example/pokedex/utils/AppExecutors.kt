package com.example.pokedex.utils

import android.os.Handler
import androidx.annotation.VisibleForTesting
import com.example.pokedex.utils.AppExecutors
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(
  private val diskIO: Executor,
  private val networkIO: Executor,
  private val mainThread: Executor
) {
  constructor() : this(
    Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT),
    MainThreadExecutor()
  )

  fun diskIO(): Executor {
    return diskIO
  }

  fun networkIO(): Executor {
    return networkIO
  }

  fun mainThread(): Executor {
    return mainThread
  }

  private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
      mainThreadHandler.post(command)
    }
  }

  companion object {
    private const val THREAD_COUNT = 3
  }
}