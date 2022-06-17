package com.hitme.android.mycocktailbar.ui.compose

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

@Composable
fun CocktailsList(
    cocktails: List<Cocktail>,
    onListItemClick: (cocktail: Cocktail) -> Unit,
    onFavoriteStateChange: (itemId: String, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (itemId: String) -> Boolean
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = cocktails, key = { it.id }) { cocktail ->
            ListItem(
                cocktail = cocktail,
                onClick = onListItemClick,
                onFavoriteStateChange = onFavoriteStateChange,
                onFavoriteStatusCheck = onFavoriteStatusCheck
            )
        }
    }
}

@Composable
fun ListItem(
    cocktail: Cocktail,
    onClick: (cocktail: Cocktail) -> Unit,
    onFavoriteStateChange: (itemId: String, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (itemId: String) -> Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(cocktail) },
        backgroundColor = MaterialTheme.colors.surface
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
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = cocktail.ingredients.joinToString(),
                    color = MaterialTheme.colors.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
            FavoriteButton(
                itemId = cocktail.id,
                onFavoriteStateChange = onFavoriteStateChange,
                isFavorite = onFavoriteStatusCheck(cocktail.id)
            )
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primaryVariant,
    itemId: String,
    isFavorite: Boolean,
    onFavoriteStateChange: (itemId: String, isFavorite: Boolean) -> Unit
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            onFavoriteStateChange(itemId, it)
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
            onFavoriteStateChange = { _, _ -> },
            onFavoriteStatusCheck = { true }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritePreview() {
    MyCocktailBarTheme(darkTheme = false) {
        FavoriteButton(
            itemId = "",
            onFavoriteStateChange = { _, _ -> },
            isFavorite = false
        )
    }
}
