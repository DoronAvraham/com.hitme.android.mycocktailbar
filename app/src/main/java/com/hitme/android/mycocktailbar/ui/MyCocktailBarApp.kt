package com.hitme.android.mycocktailbar.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme

/**
 * Main App UI entry point.
 */
@Composable
fun MyCocktailBarApp() {
    MyCocktailBarTheme(darkTheme = true) {

        val navController = rememberNavController()
        val navigationActions = remember(navController) {
            NavigationActions(navController)
        }
        val scaffoldState = rememberScaffoldState()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        Scaffold(scaffoldState = scaffoldState, bottomBar = { BottomNavBar(currentDestination, navigationActions) }) {
            NavGraph(
                paddingValues = it,
                navController = navController,
                scaffoldState = scaffoldState,
                navigationActions = navigationActions
            )
        }
    }
}

@Composable
fun BottomNavBar(currentRoute: String?, navigationActions: NavigationActions) {

    val screens = listOf(BottomNavScreen.Home, BottomNavScreen.Favorites)

    if (screens.any { it.destination == currentRoute }) {
        BottomNavigation(elevation = 10.dp) {
            screens.forEach { screen ->
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
