package com.hitme.android.mycocktailbar.data

import com.hitme.android.mycocktailbar.api.ApiService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CocktailsRemoteRepository @Inject constructor(private val httpClient: HttpClient) : ApiService {

    override suspend fun search(query: String): Flow<List<Cocktail>> {
        return flow {
            val response: DrinkSearchResponse = httpClient.get(ApiService.BASE_URL) {
                url {
                    path(ApiService.PATH_SEARCH)
                    parameters.append(ApiService.KEY_SEARCH, query)
                }
            }.body()
            emit(response.drinks)
        }.flowOn(Dispatchers.IO)
    }
}
