package com.hitme.android.mycocktailbar.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.data.CocktailsLocalRepository
import com.hitme.android.mycocktailbar.data.CocktailsRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val remoteRepository: CocktailsRemoteRepository,
    private val localRepository: CocktailsLocalRepository,
) : ViewModel() {

    private var job: Job? = null

    // Internal Mutable Stat flow that allows changes only from the ViewModel.
    private val _uiState: MutableStateFlow<DrinksUiState> = MutableStateFlow(DrinksUiState())
    // Exposed immutable State Flow that allows only read actions.
    val uiState: StateFlow<DrinksUiState> = _uiState

    init {
        // Show first results using the query "wine".
        // Later consider to use a different dedicated API (i.e. random or most popular).
        searchCocktail("wine")
        getFavorites()
    }

    /**
     * Get [query] search results via cold flow and update the [_uiState] (hot flow).
     */
    fun searchCocktail(query: String) {
        _uiState.update { it.copy(query = query, isLoading = true, cocktails = emptyList()) }
        job?.cancel()
        job = viewModelScope.launch {
            remoteRepository.search(query)
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessageId = resolveErrorMessage(error)
                        )
                    }
                }
                .collect { drinks -> _uiState.update { it.copy(isLoading = false, cocktails = drinks) } }
        }
    }

    /**
     * Add/Remove a cocktails from DB when favorite state changes.
     */
    fun onFavoriteStateChange(cocktailId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            getCocktailById(cocktailId)?.apply { localRepository.updateFavoriteState(this, isFavorite) }
        }
    }

    /**
     * Check if provided cocktails is in favorites lists.
     */
    fun onFavoriteStatusCheck(cocktailId: String): Boolean {
        return _uiState.value.favorites.any { it.id == cocktailId }
    }

    /**
     * Reset the error message after it was displayed in order to prevent displaying it again.
     */
    fun onErrorDismissed() {
        _uiState.update { it.copy(isLoading = false, errorMessageId = -1) }
    }

    fun getCocktailById(id: String): Cocktail? {
        return (_uiState.value.favorites + _uiState.value.cocktails).find { it.id == id }
    }

    fun onCocktailSelected(cocktail: Cocktail) {
        _uiState.update { it.copy(selectedCocktail = cocktail) }
    }

    /**
     * Establish a connection with the favorites DB using a Flow.
     */
    private fun getFavorites() {
        viewModelScope.launch {
            localRepository.getFavorites().collect { favorites ->
                _uiState.update { it.copy(favorites = favorites) }
            }
        }
    }

    private fun resolveErrorMessage(exception: Throwable): Int =
        if (exception is IOException) R.string.comm_error else R.string.generic_error
}

/**
 * UI state that is exposed and updated every time the internal state changes.
 *
 * @property cocktails Results to display.
 * @property favorites Items marked by the user as favorites.
 * @property query Last executed search query.
 * @property isLoading True in case a current query is running. False otherwise.
 * @property errorMessageId Error message resource ID to display.
 */
data class DrinksUiState(
    val cocktails: List<Cocktail> = emptyList(),
    val favorites: List<Cocktail> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessageId: Int = -1,
    val selectedCocktail: Cocktail = Cocktail()
)
