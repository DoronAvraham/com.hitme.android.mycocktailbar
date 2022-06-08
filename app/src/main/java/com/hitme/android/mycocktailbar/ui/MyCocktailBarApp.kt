package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hitme.android.mycocktailbar.R
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

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(contentPadding = PaddingValues(), backgroundColor = MaterialTheme.colors.background) {
                if (currentDestination == AppDestinations.HOME_SCREEN) {
                    SearchBar(Modifier.fillMaxWidth(), uiState.isLoading, cocktailsViewModel::searchCocktail)
                } else if (currentDestination == AppDestinations.DETAILS_SCREEN) {
                    DetailsTitleBar(
                        title = uiState.selectedCocktail.name,
                        cocktailId = uiState.selectedCocktail.id,
                        favorites = uiState.favorites,
                        onFavoriteStateChange = cocktailsViewModel::onFavoriteStateChange,
                        onBackClicked = navController::navigateUp
                    )
                } else {
                    TitleBar(currentDestination)
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
fun TitleBar(currentDestination: String?) {
    val screen = BottomNavScreen.screens.firstOrNull { it.destination == currentDestination }
    screen?.apply {
        Text(
            modifier = Modifier.padding(start = 10.dp),
            text = stringResource(screen.resourceId),
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, isLoading: Boolean, onClick: (String) -> Unit) {
    val text = rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier,
        value = text.value,
        label = { Text(text = stringResource(R.string.search), color = MaterialTheme.colors.onSurface) },
        leadingIcon = {
            IconButton(
                onClick = {
                    onClick(text.value)
                    focusManager.clearFocus()
                },
                enabled = text.value.isNotEmpty() && !isLoading
            ) {
                Icon(imageVector = Icons.Default.Search, "", tint = MaterialTheme.colors.onBackground)
            }
        },
        textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background),
        onValueChange = { text.value = it },
        enabled = !isLoading,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(onSearch = {
            onClick(text.value)
            focusManager.clearFocus()
        })
    )
}

@Composable
fun BottomNavBar(currentRoute: String?, navigationActions: NavigationActions) {
    if (BottomNavScreen.screens.any { it.destination == currentRoute }) {
        BottomNavigation(elevation = 10.dp, backgroundColor = MaterialTheme.colors.primary) {
            BottomNavScreen.screens.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(imageVector = screen.image, "", tint = MaterialTheme.colors.onBackground) },
                    label = { Text(text = stringResource(screen.resourceId), color = MaterialTheme.colors.onSurface) },
                    selected = currentRoute == screen.destination,
                    enabled = currentRoute != screen.destination,
                    onClick = { navigationActions.navigate(screen.destination) }
                )
            }
        }
    }
}
