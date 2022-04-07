package com.hitme.android.mycocktailbar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.hitme.android.mycocktailbar.collectAsStateLifecycleAware
import com.hitme.android.mycocktailbar.ui.compose.CircularProgressBar
import com.hitme.android.mycocktailbar.ui.compose.Home
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import com.hitme.android.mycocktailbar.ui.viewmodels.CocktailsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CocktailsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyCocktailBarTheme(darkTheme = true) {
                Surface(color = MaterialTheme.colors.background) {
                    val uiState by viewModel.uiState.collectAsStateLifecycleAware()
                    Home(
                        uiState,
                        rememberScaffoldState(),
                        viewModel::searchCocktail,
                        viewModel::onErrorDismissed,
                    )
                    if (uiState.isLoading) {
                        CircularProgressBar(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}
