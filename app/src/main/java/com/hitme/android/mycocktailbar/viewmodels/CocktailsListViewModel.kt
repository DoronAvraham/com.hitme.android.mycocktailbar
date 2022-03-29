package com.hitme.android.mycocktailbar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitme.android.mycocktailbar.data.CocktailsRepository
import com.hitme.android.mycocktailbar.data.Drink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import androidx.compose.runtime.mutableStateListOf

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val repository: CocktailsRepository
) : ViewModel() {

    var drinks = mutableStateListOf<Drink>()

    fun searchCocktail(drink: String) {
        viewModelScope.launch {
            repository.getSearchResults(drink).collect {
                drinks.clear()
                drinks.addAll(it)
            }
        }
    }
}