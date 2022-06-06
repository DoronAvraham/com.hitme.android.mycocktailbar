package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.ui.compose.CocktailsList
import com.hitme.android.mycocktailbar.ui.compose.PreviewUtils
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import com.hitme.android.mycocktailbar.ui.viewmodels.DrinksUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: DrinksUiState,
    scaffoldState: ScaffoldState,
    onSearch: (String) -> Unit,
    onErrorDismissed: () -> Unit,
    onListItemClick: (cocktail: Cocktail) -> Unit,
    onFavoriteStateChange: (itemId: String, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (itemId: String) -> Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(paddingValues)
    ) {
        CocktailsList(
            cocktails = uiState.cocktails,
            onListItemClick = onListItemClick,
            onFavoriteStateChange = onFavoriteStateChange,
            onFavoriteStatusCheck = onFavoriteStatusCheck
        )
    }

    if (uiState.errorMessageId > -1) {
        val errorMessageText = stringResource(id = uiState.errorMessageId)
        val retryMessageText = stringResource(R.string.retry)

        val onSearchState by rememberUpdatedState(onSearch)
        val onErrorDismissState by rememberUpdatedState(onErrorDismissed)

        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onSearchState(uiState.query)
            }
            // Once the message is displayed and dismissed, notify the ViewModel
            onErrorDismissState()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MyCocktailBarTheme {
        HomeScreen(
            paddingValues = PaddingValues(0.dp),
            uiState = DrinksUiState(cocktails = PreviewUtils.drinksList),
            scaffoldState = rememberScaffoldState(),
            onSearch = {},
            onErrorDismissed = {},
            onListItemClick = { },
            onFavoriteStateChange = { _, _ -> },
            onFavoriteStatusCheck = { false }
        )
    }
}
