package com.hitme.android.mycocktailbar.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CocktailsList(
    cocktails: List<Cocktail>,
    onListItemClick: (cocktail: Cocktail) -> Unit,
    onFavoriteStateChange: (cocktail: Cocktail) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = cocktails, key = { it.id }) { cocktail ->
            ListItem(
                modifier = Modifier.animateItemPlacement(),
                cocktail = cocktail,
                onClick = onListItemClick,
                onFavoriteStateChange = onFavoriteStateChange,
            )
        }
    }
}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    cocktail: Cocktail,
    onClick: (cocktail: Cocktail) -> Unit,
    onFavoriteStateChange: (cocktail: Cocktail) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(cocktail) },
    ) {
        Row {
            AsyncImage(
                modifier = Modifier.clip(MaterialTheme.shapes.medium),
                model = cocktail.thumbnailUrl,
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Text(
                    text = cocktail.name,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = cocktail.ingredients.joinToString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
            FavoriteButton(
                onFavoriteStateChange = { onFavoriteStateChange(cocktail) },
                isFavorite = cocktail.isFavorite
            )
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primaryVariant,
    isFavorite: Boolean,
    onFavoriteStateChange: () -> Unit
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            onFavoriteStateChange()
        }
    ) {
        Icon(
            tint = color,
            modifier = modifier.graphicsLayer {
                scaleX = 1.3f
                scaleY = 1.3f
            },
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    MyCocktailBarTheme {
        ListItem(
            cocktail = Cocktail(),
            onClick = {},
            onFavoriteStateChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritePreview() {
    MyCocktailBarTheme(darkTheme = false) {
        FavoriteButton(
            onFavoriteStateChange = {},
            isFavorite = false
        )
    }
}
