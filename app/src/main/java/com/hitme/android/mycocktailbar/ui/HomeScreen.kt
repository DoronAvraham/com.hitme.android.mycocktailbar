package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
    onFavoriteStateChange: (cocktail: Cocktail, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (cocktail: Cocktail) -> Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(paddingValues)
    ) {
        SearchBar(uiState.isLoading, onSearch)
        Spacer(modifier = Modifier.height(8.dp))
        CocktailsList(
            cocktails = uiState.cocktails,
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
fun SearchBar(isLoading: Boolean, onClick: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        val text = rememberSaveable { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 6.dp),
            value = text.value,
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

        IconButton(
            onClick = {
                onClick(text.value)
                focusManager.clearFocus()
            },
            enabled = text.value.isNotEmpty() && !isLoading
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = ""
            )
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
            onFavoriteStateChange = { _, _ -> },
            onFavoriteStatusCheck = { false }
        )
    }
}
