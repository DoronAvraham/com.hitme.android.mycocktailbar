package com.hitme.android.mycocktailbar.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.data.CocktailsLocalRepository
import com.hitme.android.mycocktailbar.data.CocktailsRemoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.take

@HiltWorker
class FavoritesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val localRepository: CocktailsLocalRepository,
    private val remoteRepository: CocktailsRemoteRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "FavoritesUpdateWorker started")
        localRepository.getFavorites().take(1).collect { favorites -> updateCocktails(favorites) }
        Log.d(TAG, "FavoritesUpdateWorker finished")
        return Result.success()
    }

    private suspend fun updateCocktails(favorites: List<Cocktail>) {
        coroutineScope {
            favorites
                .map { cocktail -> async { updateCocktail(cocktail) } }
                .map { deferred -> deferred.await() }
        }
    }

    private suspend fun updateCocktail(cocktail: Cocktail) {
        remoteRepository.searchById(cocktail.id).firstOrNull()?.let {
            if (cocktail != it) {
                Log.d(TAG, "Updating... OLD: $cocktail. NEW: $it")
                localRepository.updateFavoriteState(it, true)
            }
        }
    }

    companion object {
        private const val TAG = "WM-FavoritesUpdateWorker"
    }
}
