package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.ui.compose.CocktailsList
import com.hitme.android.mycocktailbar.ui.compose.PreviewUtils
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import com.hitme.android.mycocktailbar.ui.viewmodels.DrinksUiState

@Composable
fun FavoritesScreen(
    cocktails: List<Cocktail>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onFavoriteStateChange: (cocktail: Cocktail, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (cocktail: Cocktail) -> Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            .padding(paddingValues)
    ) {
        CocktailsList(
            cocktails = cocktails,
            onFavoriteStateChange = onFavoriteStateChange,
            onFavoriteStatusCheck = onFavoriteStatusCheck
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    MyCocktailBarTheme {
        FavoritesScreen(
            paddingValues = PaddingValues(0.dp),
            cocktails = PreviewUtils.drinksList,
            onFavoriteStateChange = { _, _ -> },
            onFavoriteStatusCheck = { true }
        )
    }
}
