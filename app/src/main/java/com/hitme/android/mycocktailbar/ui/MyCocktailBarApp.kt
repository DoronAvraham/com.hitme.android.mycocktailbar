package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hitme.android.mycocktailbar.DataStoreManager
import com.hitme.android.mycocktailbar.collectAsStateLifecycleAware
import com.hitme.android.mycocktailbar.ui.viewmodels.CocktailsListViewModel
import kotlinx.coroutines.launch

/**
 * Main App UI entry point.
 */
@Composable
fun MyCocktailBarApp(dataStoreManager: DataStoreManager) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val scaffoldState = rememberScaffoldState()

    val cocktailsViewModel = hiltViewModel<CocktailsListViewModel>()
    val uiState by cocktailsViewModel.uiState.collectAsStateLifecycleAware()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val scope = rememberCoroutineScope()
    val onToggleDarkMode: () -> Unit = { scope.launch { dataStoreManager.toggleDarkMode() } }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                contentPadding = PaddingValues(vertical = 8.dp),
            ) {
                // Override the default TopAppBar ContentAlpha.medium.
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                    when (currentDestination) {
                        AppDestinations.HOME_SCREEN -> SearchBar(
                            Modifier.fillMaxWidth(),
                            uiState.isLoading,
                            cocktailsViewModel::searchCocktail,
                            onToggleDarkMode
                        )
                        AppDestinations.DETAILS_SCREEN -> DetailsTitleBar(
                            title = uiState.selectedCocktail.name,
                            cocktailId = uiState.selectedCocktail.id,
                            favorites = uiState.favorites,
                            onFavoriteStateChange = cocktailsViewModel::onFavoriteStateChange,
                            onBackClicked = navController::navigateUp
                        )
                        else -> FavoritesTitleBar(currentDestination = currentDestination)
                    }
                }
            }
        },
        bottomBar = { BottomNavBar(currentDestination, navigationActions) }
    ) {
        NavGraph(
            paddingValues = it,
            navController = navController,
            scaffoldState = scaffoldState,
            navigationActions = navigationActions,
            cocktailsViewModel = cocktailsViewModel,
            uiState = uiState
        )
    }
}

@Composable
fun BottomNavBar(currentRoute: String?, navigationActions: NavigationActions) {
    if (BottomNavScreen.screens.any { it.destination == currentRoute }) {
        BottomNavigation(elevation = 10.dp) {
            BottomNavScreen.screens.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = screen.image, "") },
                    label = { Text(text = stringResource(screen.resourceId)) },
                    selected = currentRoute == screen.destination,
                    enabled = currentRoute != screen.destination,
                    onClick = { navigationActions.navigate(screen.destination) }
                )
            }
        }
    }
}
