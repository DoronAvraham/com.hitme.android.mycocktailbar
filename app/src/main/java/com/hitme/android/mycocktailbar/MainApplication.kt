package com.hitme.android.mycocktailbar

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.hitme.android.mycocktailbar.workers.FavoritesUpdateWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<FavoritesUpdateWorker>(INTERVAL, TimeUnit.HOURS).build()
        workManager.enqueueUniquePeriodicWork(WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest)
    }

    companion object {
        private const val INTERVAL = 24L
        private const val WORK_NAME = "UpdateFavorites"
    }
}
