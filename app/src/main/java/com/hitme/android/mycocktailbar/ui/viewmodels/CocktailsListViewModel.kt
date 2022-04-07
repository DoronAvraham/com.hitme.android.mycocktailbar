package com.hitme.android.mycocktailbar.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.data.CocktailsRepository
import com.hitme.android.mycocktailbar.data.Drink
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
    private val repository: CocktailsRepository
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
    }

    /**
     * Get [query] search results via cold flow and update the [_uiState] (hot flow).
     */
    fun searchCocktail(query: String) {
        _uiState.update { it.copy(query = query, isLoading = true, drinks = emptyList()) }
        job?.cancel()
        job = viewModelScope.launch {
            repository.getSearchResults(query)
                .catch { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessageId = resolveErrorMessage(error)
                        )
                    }
                }
                .collect { drinks -> _uiState.update { it.copy(isLoading = false, drinks = drinks) } }
        }
    }

    /**
     * Reset the error message after it was displayed in order to prevent displaying it again.
     */
    fun onErrorDismissed() {
        _uiState.update { it.copy(isLoading = false, errorMessageId = -1) }
    }

    private fun resolveErrorMessage(exception: Throwable): Int =
        if (exception is IOException) R.string.comm_error else R.string.generic_error
}

/**
 * UI state that is exposed and updated every time the internal state changes.
 *
 * @property drinks Results to display.
 * @property query Last executed search query.
 * @property isLoading True in case a current query is running. False otherwise.
 * @property errorMessageId Error message resource ID to display.
 */
data class DrinksUiState(
    val drinks: List<Drink> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessageId: Int = -1
)
