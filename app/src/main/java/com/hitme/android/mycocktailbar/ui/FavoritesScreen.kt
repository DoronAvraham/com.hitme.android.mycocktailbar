package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    onFavoriteStateChange: (cocktail: Cocktail) -> Unit,
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
        )
    }
}

@Composable
fun FavoritesTitleBar(modifier: Modifier = Modifier, currentDestination: String?) {
    val screen = BottomNavScreen.screens.firstOrNull { it.destination == currentDestination }
    screen?.apply {
        Text(
            modifier = modifier.padding(start = 10.dp),
            text = stringResource(screen.resourceId),
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
            onFavoriteStateChange = {},
        )
    }
}
