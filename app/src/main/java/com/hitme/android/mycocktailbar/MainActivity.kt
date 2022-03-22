package com.hitme.android.mycocktailbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Present(viewModel.uiState) { viewModel.searchCocktail("Margarita") }
                }
            }
        }
    }
}

@Composable
fun Present(drinks: List<Drink>, onClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            modifier = Modifier.wrapContentSize(),
            onClick = onClick) {
            Text(
                "Get Cocktails",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        Text(
            drinks.toString(),
            Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyCocktailBarTheme {
        Present(listOf()) {}
    }
}