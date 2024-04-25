package com.hitme.android.mycocktailbar.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.Result
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.data.CocktailsLocalRepository
import com.hitme.android.mycocktailbar.data.CocktailsRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val remoteRepository: CocktailsRemoteRepository,
    private val localRepository: CocktailsLocalRepository,
) : ViewModel() {

    private var job: Job? = null

    var query by mutableStateOf("")
        private set

    var selectedCocktail: Cocktail? by mutableStateOf(null)
        private set

    private val _cocktailsFlow: MutableStateFlow<Result<List<Cocktail>>> = MutableStateFlow(Result.Success(emptyList()))
    private val _favoritesFlow = localRepository.getFavorites()
    // Exposed immutable State Flow that allows only read actions.
    val uiState: StateFlow<DrinksUiState> = combine(
        _cocktailsFlow,
        _favoritesFlow
    ) { result, favorites ->
        when (result) {
            is Result.Loading -> DrinksUiState(isLoading = true, cocktails = emptyList(), favorites = favorites)
            is Result.Success -> {
                val results = result.data.map {
                    it.isFavorite = favorites.contains(it)
                    it
                }
                DrinksUiState(isLoading = false, cocktails = results, favorites = favorites)
            }
            is Result.Error -> DrinksUiState(
                isLoading = false,
                errorMessageId = resolveErrorMessage(result.exception),
                favorites = favorites
            )
            is Result.ErrorDismissed -> DrinksUiState(
                isLoading = false,
                errorMessageId = -1
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DrinksUiState()
    )

    fun searchCocktail() {
        searchCocktail(query)
    }

    private fun searchCocktail(query: String) {

        job?.cancel()
        job = viewModelScope.launch {
            remoteRepository.search(query)
                .collect { result -> _cocktailsFlow.emit(result) }
        }
    }

    /**
     * Store the search text state.
     */
    fun onSearchTextChange(text: String) {
        query = text
    }

    /**
     * Add/Remove a cocktails from DB when favorite state changes.
     */
    fun onFavoriteStateChange(cocktail: Cocktail) {
        viewModelScope.launch {
            localRepository.updateFavoriteState(cocktail, !cocktail.isFavorite)
        }
    }

    /**
     * Reset the error message after it was displayed in order to prevent displaying it again.
     */
    fun onErrorDismissed() {
        viewModelScope.launch {
            _cocktailsFlow.emit(Result.Success(emptyList()))
            _cocktailsFlow.emit(Result.ErrorDismissed)
        }
    }

    fun onSelectedCocktailStateChanged(cocktail: Cocktail? = null) {
        selectedCocktail = cocktail
    }

    private fun resolveErrorMessage(exception: Throwable?): Int =
        if (exception is IOException) R.string.comm_error else R.string.generic_error
}

/**
 * UI state that is exposed and updated every time the internal state changes.
 *
 * @property cocktails Results to display.
 * @property favorites Items marked by the user as favorites.
 * @property isLoading True in case a current query is running. False otherwise.
 * @property errorMessageId Error message resource ID to display.
 */
data class DrinksUiState(
    val cocktails: List<Cocktail> = emptyList(),
    val favorites: List<Cocktail> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessageId: Int = -1,
)
