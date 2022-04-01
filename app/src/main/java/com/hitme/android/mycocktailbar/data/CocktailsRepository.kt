package com.hitme.android.mycocktailbar.data

import com.hitme.android.mycocktailbar.api.CocktailsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CocktailsRepository @Inject constructor(private val cocktailsService: CocktailsService) {

    fun getSearchResults(query: String): Flow<List<Drink>> {
        return flow {
            val response = cocktailsService.search(query)
            emit(response.drinks)
        }.flowOn(Dispatchers.IO)
    }
}
