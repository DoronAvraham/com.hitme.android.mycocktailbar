package com.hitme.android.mycocktailbar.di

import com.hitme.android.mycocktailbar.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return ApiService.createClient()
    }
}
