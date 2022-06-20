package com.hitme.android.mycocktailbar.api

import com.hitme.android.mycocktailbar.data.Cocktail
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

interface ApiService {

    suspend fun search(query: String): Flow<List<Cocktail>>

    companion object {

        const val BASE_URL = "https://www.thecocktaildb.com/"
        const val PATH_SEARCH = "api/json/v1/1/search.php"
        const val KEY_SEARCH = "s"

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
                }
            }
        }
    }
}
