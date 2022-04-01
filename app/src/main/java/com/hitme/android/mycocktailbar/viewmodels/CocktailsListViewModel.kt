package com.hitme.android.mycocktailbar.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hitme.android.mycocktailbar.data.CocktailsRepository
import com.hitme.android.mycocktailbar.data.Drink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsListViewModel @Inject constructor(
    private val repository: CocktailsRepository
) : ViewModel() {

    var job: Job? = null

    private val _result: MutableStateFlow<List<Drink>> = MutableStateFlow(emptyList())
    val result: StateFlow<List<Drink>> = _result

    init {
        searchCocktail("wine")
    }

    fun searchCocktail(drink: String) {
        job?.cancel()
        job = viewModelScope.launch {
            repository.getSearchResults(drink)
                .collect {
                    _result.emit(it)
                }
        }
    }
}
