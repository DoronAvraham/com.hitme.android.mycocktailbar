package com.hitme.android.mycocktailbar.ui

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.ui.compose.FavoriteButton
import com.hitme.android.mycocktailbar.ui.compose.GlideImage
import com.hitme.android.mycocktailbar.ui.compose.PreviewUtils
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme

@Composable
fun DetailsScreen(
    cocktail: Cocktail,
    onFavoriteStateChange: (itemId: String, isFavorite: Boolean) -> Unit,
    onFavoriteStatusCheck: (itemId: String) -> Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = cocktail.name, Modifier.weight(1f))
            FavoriteButton(
                itemId = cocktail.id,
                isFavorite = onFavoriteStatusCheck(cocktail.id),
                onFavoriteStateChange = onFavoriteStateChange
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val ingredients: List<String> = cocktail.ingredients.mapIndexed { index, ingredient ->
                    "$ingredient ${cocktail.measures[index]}"
                }
                ingredients.forEach {
                    Text(text = it, modifier = Modifier.padding(5.dp), color = MaterialTheme.colors.onSurface)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = cocktail.instructions,
                    modifier = Modifier.padding(10.dp),
                    color = MaterialTheme.colors.onSurface,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    MyCocktailBarTheme {
        DetailsScreen(
            cocktail = PreviewUtils.cocktail,
            onFavoriteStateChange = { _, _ -> },
            onFavoriteStatusCheck = { false }
        )
    }
}
