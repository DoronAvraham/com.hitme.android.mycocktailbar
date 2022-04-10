package com.hitme.android.mycocktailbar.ui

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the application.
 */
object AppDestinations {
    const val HOME_SCREEN = "home"
    const val FAVORITES_SCREEN = "favorites"
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(navController: NavHostController) {
    val navigateToHomeScreen: () -> Unit = {
        navController.navigate(AppDestinations.HOME_SCREEN) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToFavoritesScreen: () -> Unit = {
        navController.navigate(AppDestinations.FAVORITES_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
