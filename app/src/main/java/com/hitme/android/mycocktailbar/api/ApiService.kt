package com.hitme.android.mycocktailbar.api

import android.util.Log
import com.hitme.android.mycocktailbar.BuildConfig
import com.hitme.android.mycocktailbar.data.Cocktail
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.MessageLengthLimitingLogger
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

interface ApiService {

    suspend fun search(query: String): Flow<List<Cocktail>>

    suspend fun searchById(id: String): List<Cocktail>

    companion object {

        const val BASE_URL = "https://www.thecocktaildb.com/"
        const val PATH_SEARCH = "api/json/v1/1/search.php"
        const val PATH_SEARCH_BY_ID = "api/json/v1/1/lookup.php"
        const val KEY_SEARCH = "s"
        const val KEY_SEARCH_BY_ID = "i"

        const val LOGGER_TAG = "HttpClientLog"

        fun createClient(): HttpClient {
            return HttpClient(Android) {

                install(ContentNegotiation) {
                    json(
                        Json {
                            coerceInputValues = true
                            ignoreUnknownKeys = true
                        }
                    )
                }

                install(Logging) {
                    level = LogLevel.ALL
                    if (BuildConfig.DEBUG) {
                        logger = Logger.CUSTOM
                    }
                }
            }
        }

        private val Logger.Companion.CUSTOM: Logger
            get() = MessageLengthLimitingLogger(
                delegate = object : Logger {
                    override fun log(message: String) {
                        Log.d(LOGGER_TAG, message)
                    }
                }
            )
    }
}
