package com.hitme.android.mycocktailbar.ui

import androidx.navigation.NavHostController
import com.hitme.android.mycocktailbar.ui.AppDestinations.HOME_SCREEN

/**
 * Destinations used in the application.
 */
object AppDestinations {
    const val HOME_SCREEN = "home"
    const val FAVORITES_SCREEN = "favorites"
    const val DETAILS_SCREEN = "details"
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(navController: NavHostController) {
    val navigateToHomeScreen: () -> Unit = {
        navController.popBackStack()
        navController.navigate(HOME_SCREEN) {
            launchSingleTop = true
        }
    }
    val navigateToFavoritesScreen: () -> Unit = {
        navController.popBackStack()
        navController.navigate(AppDestinations.FAVORITES_SCREEN) {
            launchSingleTop = true
        }
    }
}
