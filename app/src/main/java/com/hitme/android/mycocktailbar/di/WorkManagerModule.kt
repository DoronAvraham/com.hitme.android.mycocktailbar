package com.hitme.android.mycocktailbar.di

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.hitme.android.mycocktailbar.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WorkManagerModule {

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context, workerFactory: HiltWorkerFactory): WorkManager {

        val logLevel = if (BuildConfig.DEBUG) Log.DEBUG else Log.INFO
        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(logLevel)
            .build()

        WorkManager.initialize(context, configuration)
        return WorkManager.getInstance(context)
    }
}
