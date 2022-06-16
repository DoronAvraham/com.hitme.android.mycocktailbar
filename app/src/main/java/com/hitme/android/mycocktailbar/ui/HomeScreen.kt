package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
            .padding(10.dp)
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

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onSearch: (String) -> Unit,
    onToggleDarkMode: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val focusManagerState by remember { mutableStateOf(focusManager) }

    val search = {
        onSearch(text)
        focusManagerState.clearFocus()
    }

    val enabled by rememberSaveable(text, isLoading) { mutableStateOf(text.isNotEmpty() && !isLoading) }

    Row(
        modifier = modifier.background(MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            value = text,
            label = { Text(text = stringResource(R.string.search), color = MaterialTheme.colors.onPrimary) },
            leadingIcon = {
                IconButton(
                    onClick = search,
                    enabled = enabled
                ) {
                    Icon(imageVector = Icons.Default.Search, "", tint = MaterialTheme.colors.onPrimary)
                }
            },
            textStyle = TextStyle(color = MaterialTheme.colors.onPrimary),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.primary,
                focusedIndicatorColor = MaterialTheme.colors.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            onValueChange = { text = it },
            enabled = !isLoading,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                search()
            })
        )

        IconButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = onToggleDarkMode
        ) {
            Icon(imageVector = Icons.Default.MoreVert, "", tint = MaterialTheme.colors.onPrimary)
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
            onListItemClick = {},
            onFavoriteStateChange = { _, _ -> },
            onFavoriteStatusCheck = { false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    MyCocktailBarTheme {
        SearchBar(isLoading = false, onSearch = {}, onToggleDarkMode = {})
    }
}
