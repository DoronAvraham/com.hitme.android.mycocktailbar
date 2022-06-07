package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hitme.android.mycocktailbar.ui.compose.CircularProgressBar
import com.hitme.android.mycocktailbar.ui.viewmodels.CocktailsListViewModel
import com.hitme.android.mycocktailbar.ui.viewmodels.DrinksUiState

/**
 * Main navigation class responsible for returning a composable for each app screen.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    scaffoldState: ScaffoldState,
    navigationActions: NavigationActions,
    cocktailsViewModel: CocktailsListViewModel,
    uiState: DrinksUiState
) {
    NavHost(navController = navController, startDestination = AppDestinations.HOME_SCREEN) {
        composable(AppDestinations.HOME_SCREEN) {
            HomeScreen(
                uiState = uiState,
                paddingValues = paddingValues,
                scaffoldState = scaffoldState,
                onSearch = cocktailsViewModel::searchCocktail,
                onErrorDismissed = cocktailsViewModel::onErrorDismissed,
                onFavoriteStateChange = cocktailsViewModel::onFavoriteStateChange,
                onFavoriteStatusCheck = cocktailsViewModel::onFavoriteStatusCheck,
                onListItemClick = { cocktail ->
                    cocktailsViewModel.onCocktailSelected(cocktail)
                    navigationActions.navigate(AppDestinations.DETAILS_SCREEN)
                }
            )
            if (uiState.isLoading) {
                CircularProgressBar(modifier = Modifier.fillMaxSize())
            }
        }
        composable(AppDestinations.FAVORITES_SCREEN) {
            FavoritesScreen(
                favorites = uiState.favorites,
                paddingValues = paddingValues,
                onFavoriteStateChange = cocktailsViewModel::onFavoriteStateChange,
                onFavoriteStatusCheck = { true },
                onListItemClick = { cocktail ->
                    cocktailsViewModel.onCocktailSelected(cocktail)
                    navigationActions.navigate(AppDestinations.DETAILS_SCREEN)
                }
            )
        }
        composable(AppDestinations.DETAILS_SCREEN) {
            DetailsScreen(
                cocktail = uiState.selectedCocktail
            )
        }
        composable(AppDestinations.SETTINGS_SCREEN) {
            SettingsScreen()
        }
    }
}
