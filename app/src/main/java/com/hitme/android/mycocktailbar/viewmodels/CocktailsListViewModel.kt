package com.hitme.android.mycocktailbar.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitme.android.mycocktailbar.data.CocktailsRepository
import com.hitme.android.mycocktailbar.data.Drink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val repository: CocktailsRepository
) : ViewModel() {

    var uiState by mutableStateOf<List<Drink>>(listOf())
        private set

    fun searchCocktail(query: String) {
        viewModelScope.launch {
            repository.getSearchResults(query).collect {
                uiState = it
            }
        }
    }
}