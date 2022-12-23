package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
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
    text: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onToggleDarkMode: () -> Unit
) {
    val focusManagerState by rememberUpdatedState(newValue = LocalFocusManager.current)

    val search = {
        onSearch(text)
        focusManagerState.clearFocus()
    }

    val enabled by rememberSaveable(text, isLoading) { mutableStateOf(text.isNotEmpty() && !isLoading) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            value = text,
            label = { Text(text = stringResource(R.string.search)) },
            leadingIcon = {
                IconButton(
                    onClick = search,
                    enabled = enabled
                ) {
                    Icon(imageVector = Icons.Default.Search, "")
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = LocalContentColor.current,
                focusedIndicatorColor = LocalContentColor.current,
                unfocusedIndicatorColor = LocalContentColor.current,
                focusedLabelColor = LocalContentColor.current,
                unfocusedLabelColor = LocalContentColor.current,
                cursorColor = LocalContentColor.current,
                leadingIconColor = LocalContentColor.current
            ),
            onValueChange = onValueChange,
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
            Icon(imageVector = Icons.Default.MoreVert, "")
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
        SearchBar(isLoading = false, text = "", onSearch = {}, onToggleDarkMode = {}, onValueChange = {})
    }
}
