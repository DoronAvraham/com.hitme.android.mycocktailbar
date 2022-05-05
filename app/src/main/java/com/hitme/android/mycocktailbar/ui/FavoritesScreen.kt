package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.ui.compose.CocktailsList
import com.hitme.android.mycocktailbar.ui.compose.PreviewUtils
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme

@Composable
fun FavoritesScreen(
    favorites: List<Cocktail>,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
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
            cocktails = favorites,
            onListItemClick = onListItemClick,
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
            favorites = PreviewUtils.drinksList,
            onListItemClick = { },
            onFavoriteStateChange = { _, _ -> },
            onFavoriteStatusCheck = { true }
        )
    }
}
