package com.hitme.android.mycocktailbar.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hitme.android.mycocktailbar.data.CocktailsLocalRepository
import com.hitme.android.mycocktailbar.data.CocktailsRemoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.withContext

@HiltWorker
class FavoritesUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val localRepository: CocktailsLocalRepository,
    private val remoteRepository: CocktailsRemoteRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            localRepository.getFavorites().take(1).collect { favorites ->
                favorites.forEach { cocktail ->
                    remoteRepository.searchById(cocktail.id).forEach {
                        if (cocktail != it) {
                            Log.d("WORKER", "Cocktail ${cocktail.name} changed. Updating DB.")
                            localRepository.updateFavoriteState(it, true)
                        } else {
                            Log.d("WORKER", "Cocktail ${cocktail.name} no changed.")
                        }
                    }
                }
            }
        }
        return Result.success()
    }
}
