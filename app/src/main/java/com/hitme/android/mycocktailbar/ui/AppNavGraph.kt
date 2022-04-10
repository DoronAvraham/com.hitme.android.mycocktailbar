package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hitme.android.mycocktailbar.collectAsStateLifecycleAware
import com.hitme.android.mycocktailbar.ui.compose.CircularProgressBar
import com.hitme.android.mycocktailbar.ui.viewmodels.CocktailsListViewModel

/**
 * Main navigation class responsible for returning a composable for each app screen.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    scaffoldState: ScaffoldState
) {
    NavHost(navController = navController, startDestination = AppDestinations.HOME_SCREEN) {
        composable(AppDestinations.HOME_SCREEN) {
            val cocktailsViewModel = hiltViewModel<CocktailsListViewModel>()
            val uiState by cocktailsViewModel.uiState.collectAsStateLifecycleAware()
            HomeScreen(
                uiState = uiState,
                paddingValues = paddingValues,
                scaffoldState = scaffoldState,
                onSearch = cocktailsViewModel::searchCocktail,
                onErrorDismissed = cocktailsViewModel::onErrorDismissed
            )
            if (uiState.isLoading) {
                CircularProgressBar(modifier = Modifier.fillMaxSize())
            }
        }
        composable(AppDestinations.FAVORITES_SCREEN) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // TODO replace with the implementation of favorites screen.
                Text(text = "Favorites screen")
            }
        }
    }
}
