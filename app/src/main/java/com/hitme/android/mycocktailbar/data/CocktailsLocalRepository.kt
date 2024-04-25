package com.hitme.android.mycocktailbar.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CocktailsLocalRepository @Inject constructor(private val cocktailDao: CocktailDao) {

    fun getFavorites(): Flow<List<Cocktail>> {
        return cocktailDao.getCocktails().map { cocktails ->
            cocktails.onEach { it.isFavorite = true }
        }
    }

    suspend fun updateFavoriteState(cocktail: Cocktail, isFavorite: Boolean) {
        if (isFavorite) cocktailDao.insert(cocktail) else cocktailDao.delete(cocktail)
    }
}
