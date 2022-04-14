package com.hitme.android.mycocktailbar.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
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

        Scaffold(scaffoldState = scaffoldState, bottomBar = { BottomNavBar(navigationActions) }) {
            NavGraph(paddingValues = it, navController = navController, scaffoldState = scaffoldState)
        }
    }
}

@Composable
fun BottomNavBar(navigationActions: NavigationActions) {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    BottomNavigation(elevation = 10.dp) {
        BottomNavigationItem(
            label = { Text(text = "Home") },
            selected = selectedIndex == 0,
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            onClick = {
                selectedIndex = 0
                navigationActions.navigateToHomeScreen()
            }
        )
        BottomNavigationItem(
            label = { Text(text = "Favorites") },
            selected = selectedIndex == 1,
            icon = {
                Icon(imageVector = Icons.Default.Favorite, "")
            },
            onClick = {
                selectedIndex = 1
                navigationActions.navigateToFavoritesScreen()
            }
        )
    }
}
