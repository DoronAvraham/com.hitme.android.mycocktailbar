package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hitme.android.mycocktailbar.collectAsStateLifecycleAware
import com.hitme.android.mycocktailbar.ui.viewmodels.CocktailsListViewModel

/**
 * Main App UI entry point.
 */
@Composable
fun MyCocktailBarApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val scaffoldState = rememberScaffoldState()

    val cocktailsViewModel = hiltViewModel<CocktailsListViewModel>()
    val uiState by cocktailsViewModel.uiState.collectAsStateLifecycleAware()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val onBackPressed: () -> Unit = { navController.navigateUp() }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                contentPadding =
                PaddingValues(8.dp),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                if (currentDestination == AppDestinations.HOME_SCREEN) {
                    SearchBar(
                        Modifier.fillMaxWidth(),
                        uiState.isLoading,
                        cocktailsViewModel::searchCocktail
                    )
                } else if (currentDestination == AppDestinations.DETAILS_SCREEN) {
                    DetailsTitleBar(
                        title = uiState.selectedCocktail.name,
                        cocktailId = uiState.selectedCocktail.id,
                        favorites = uiState.favorites,
                        onFavoriteStateChange = cocktailsViewModel::onFavoriteStateChange,
                        onBackClicked = onBackPressed
                    )
                } else {
                    TitleBar(currentDestination = currentDestination)
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
fun TitleBar(modifier: Modifier = Modifier, currentDestination: String?) {
    val screen = BottomNavScreen.screens.firstOrNull { it.destination == currentDestination }
    screen?.apply {
        Text(
            modifier = modifier.padding(start = 10.dp),
            text = stringResource(screen.resourceId),
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun BottomNavBar(currentRoute: String?, navigationActions: NavigationActions) {
    if (BottomNavScreen.screens.any { it.destination == currentRoute }) {
        BottomNavigation(elevation = 10.dp, backgroundColor = MaterialTheme.colors.primary) {
            BottomNavScreen.screens.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = screen.image, "", tint = MaterialTheme.colors.onPrimary) },
                    label = { Text(text = stringResource(screen.resourceId), color = MaterialTheme.colors.onPrimary) },
                    selected = currentRoute == screen.destination,
                    enabled = currentRoute != screen.destination,
                    onClick = { navigationActions.navigate(screen.destination) }
                )
            }
        }
    }
}
