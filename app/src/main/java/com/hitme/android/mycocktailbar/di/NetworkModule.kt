package com.hitme.android.mycocktailbar.di

import com.hitme.android.mycocktailbar.api.CocktailsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCocktailService(): CocktailsService {
        return CocktailsService.create()
    }
}