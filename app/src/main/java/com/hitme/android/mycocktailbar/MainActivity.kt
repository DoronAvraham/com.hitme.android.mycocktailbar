package com.hitme.android.mycocktailbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.data.Drink
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import com.hitme.android.mycocktailbar.viewmodels.CocktailsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CocktailsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCocktailBarTheme {
                Surface {
                    PresentList(
                        viewModel.drinks,
                        viewModel::searchCocktail
                    )
                }
            }
        }
    }
}

@Composable
fun PresentList(
    drinks: List<Drink>,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        SearchBar(onClick)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(items = drinks, key = { drink -> drink.id }) {
                ListItem(it.name, it.ingredients, it.thumbnailUrl)
            }
        }
    }
}

@Composable
fun SearchBar(onClick: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val text = rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 6.dp),
            value = text.value,
            onValueChange = { text.value = it },
            maxLines = 1,
            singleLine = true
        )
        IconButton(onClick = { onClick(text.value) }, enabled = text.value.isNotEmpty()) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ListItem(name: String, ingredients: List<String>, imageUrl: String) {
    Card(shape = RoundedCornerShape(3.dp), elevation = 12.dp) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                modifier = Modifier
                    .size(128.dp, 128.dp)
                    .padding(end = 8.dp),
                contentDescription = "",
                data = imageUrl,
                placeHolderDrawable = AppCompatResources.getDrawable(
                    LocalContext.current,
                    R.drawable.ic_launcher_foreground
                ),
                glideModifier = { requestBuilder ->
                    requestBuilder.centerCrop()
                }
            )
            Column {
                Text(
                    text = name,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = ingredients.joinToString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCocktailBarTheme {
        PresentList(PreviewUtils.drinksList) {}
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    ListItem("Margarita", PreviewUtils.ingredients, "")
}
