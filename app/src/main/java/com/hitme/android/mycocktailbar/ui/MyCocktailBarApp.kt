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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
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

        Scaffold(scaffoldState = scaffoldState, bottomBar = { BottomNavBar(navController, navigationActions) }) {
            NavGraph(
                paddingValues = it,
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController, navigationActions: NavigationActions) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screens = listOf(BottomNavScreen.Home, BottomNavScreen.Favorites)

    BottomNavigation(elevation = 10.dp) {
        screens.forEach { screen ->
            val currentScreen = currentDestination?.hierarchy?.any { it.route == screen.destination } == true
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.image, "") },
                label = { Text(text = stringResource(screen.resourceId)) },
                selected = currentScreen,
                enabled = !currentScreen,
                onClick = { navigationActions.navigate(screen.destination) }
            )
        }
    }
}
