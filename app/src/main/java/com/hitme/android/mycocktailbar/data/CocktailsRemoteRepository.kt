package com.hitme.android.mycocktailbar.data

import com.hitme.android.mycocktailbar.Result
import com.hitme.android.mycocktailbar.api.ApiService
import com.hitme.android.mycocktailbar.asResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CocktailsRemoteRepository @Inject constructor(private val httpClient: HttpClient) : ApiService {

    override suspend fun search(query: String): Flow<Result<List<Cocktail>>> {
        return flow {
            val response: DrinkSearchResponse = httpClient.get(ApiService.BASE_URL) {
                url {
                    path(ApiService.PATH_SEARCH)
                    parameters.append(ApiService.KEY_SEARCH, query)
                }
            }.body()
            emit(response.drinks)
        }
            .asResult()
            .flowOn(Dispatchers.IO)
    }

    override suspend fun searchById(id: String): List<Cocktail> {
        return withContext(Dispatchers.IO) {
            val response: DrinkSearchResponse = httpClient.get(ApiService.BASE_URL) {
                url {
                    path(ApiService.PATH_SEARCH_BY_ID)
                    parameters.append(ApiService.KEY_SEARCH_BY_ID, id)
                }
            }.body()
            response.drinks
        }
    }
}
