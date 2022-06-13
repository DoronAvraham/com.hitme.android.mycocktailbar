package com.hitme.android.mycocktailbar.ui

import androidx.annotation.StringDef
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.ui.AppDestinations.Companion.DETAILS_SCREEN
import com.hitme.android.mycocktailbar.ui.AppDestinations.Companion.FAVORITES_SCREEN
import com.hitme.android.mycocktailbar.ui.AppDestinations.Companion.HOME_SCREEN

/**
 * Destinations used in the application.
 */
@Retention(AnnotationRetention.SOURCE)
@StringDef(HOME_SCREEN, FAVORITES_SCREEN, DETAILS_SCREEN)
annotation class AppDestinations {

    companion object {
        const val HOME_SCREEN = "home"
        const val FAVORITES_SCREEN = "favorites"
        const val DETAILS_SCREEN = "details"
    }
}

/**
 * Models the navigation actions in the app.
 */
class NavigationActions(private val navController: NavHostController) {

    fun navigate(@AppDestinations destination: String) {
        navController.navigate(destination) {
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
}

/**
 * Represent all possible bottom bar destinations.
 */
sealed class BottomNavScreen(val destination: String, @StringRes val resourceId: Int, val image: ImageVector) {
    object Home : BottomNavScreen(HOME_SCREEN, R.string.nav_btn_home, Icons.Default.Home)
    object Favorites : BottomNavScreen(FAVORITES_SCREEN, R.string.nav_btn_favorites, Icons.Default.Favorite)

    companion object {
        val screens = listOf(Home, Favorites)
    }
}
