package com.hitme.android.mycocktailbar.api

import com.hitme.android.mycocktailbar.data.DrinkSearchResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


interface CocktailsService {

    @GET("/api/json/v1/1/search.php")
    suspend fun search(@Query("s") query: String): DrinkSearchResponse

    companion object {
        private const val BASE_URL = "https://www.thecocktaildb.com/"

        private val MEDIA_TYPE = "application/json".toMediaType()
        private val json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }

        fun create(): CocktailsService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(json.asConverterFactory(MEDIA_TYPE))
                .build()
                .create(CocktailsService::class.java)
        }
    }
}