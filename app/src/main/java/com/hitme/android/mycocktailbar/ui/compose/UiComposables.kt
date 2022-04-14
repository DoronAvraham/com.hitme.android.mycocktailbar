package com.hitme.android.mycocktailbar.ui.compose

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme

@Composable
fun CocktailsList(
    cocktails: List<Cocktail>,
    onFavoriteStateChange: (item: Cocktail, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (item: Cocktail) -> Boolean
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = cocktails, key = { it.id }) { cocktail ->
            ListItem(
                cocktail,
                onFavoriteStateChange = onFavoriteStateChange,
                onFavoriteStatusCheck = onFavoriteStatusCheck
            )
        }
    }
}

@Composable
fun ListItem(
    cocktail: Cocktail,
    onFavoriteStateChange: (item: Cocktail, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (item: Cocktail) -> Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Row {
            GlideImage(
                modifier = Modifier
                    .size(140.dp, 120.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentDescription = "",
                data = cocktail.thumbnailUrl,
                placeHolderDrawable = AppCompatResources.getDrawable(
                    LocalContext.current,
                    R.drawable.ic_launcher_foreground
                ),
                glideModifier = { requestBuilder ->
                    requestBuilder.centerCrop()
                }
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
                item = cocktail,
                onFavoriteStateChange = onFavoriteStateChange,
                isFavorite = onFavoriteStatusCheck(cocktail)
            )
        }
    }
}

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.secondary,
    item: Cocktail,
    isFavorite: Boolean,
    onFavoriteStateChange: (item: Cocktail, isFavorite: Boolean) -> Unit
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = {
            onFavoriteStateChange(item, it)
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
            item = Cocktail(),
            onFavoriteStateChange = { _, _ -> },
            isFavorite = false
        )
    }
}
