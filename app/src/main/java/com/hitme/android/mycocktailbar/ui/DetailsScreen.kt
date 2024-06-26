package com.hitme.android.mycocktailbar.ui

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hitme.android.mycocktailbar.data.Cocktail
import com.hitme.android.mycocktailbar.ui.compose.PreviewUtils
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme

@Composable
fun DetailsScreen(
    cocktail: Cocktail
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(180.dp)
                .clip(MaterialTheme.shapes.medium),
            model = cocktail.thumbnailUrl,
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val ingredients: List<String> = cocktail.ingredients.mapIndexed { index, ingredient ->
                    "$ingredient ${cocktail.measures[index]}"
                }
                ingredients.forEach {
                    Text(text = it, modifier = Modifier.padding(5.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = cocktail.instructions,
                    modifier = Modifier.padding(10.dp),
                )
            }
        }
    }
}

@Composable
fun DetailsTitleBar(
    modifier: Modifier = Modifier,
    cocktailName: String,
    onBackClicked: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onBackClicked,
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, "")
        }

        Text(
            modifier = Modifier.weight(1f),
            text = cocktailName,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    MyCocktailBarTheme {
        DetailsScreen(
            cocktail = PreviewUtils.cocktail
        )
    }
}
