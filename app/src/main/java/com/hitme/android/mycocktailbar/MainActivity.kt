package com.hitme.android.mycocktailbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import com.hitme.android.mycocktailbar.viewmodels.CocktailsListViewModel
import com.hitme.android.mycocktailbar.viewmodels.DrinksUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CocktailsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyCocktailBarTheme(darkTheme = true) {
                Surface(color = MaterialTheme.colors.background) {
                    val uiState by viewModel.uiState.collectAsStateLifecycleAware()
                    PresentList(
                        uiState,
                        rememberScaffoldState(),
                        viewModel::searchCocktail,
                        viewModel::onErrorDismissed,

                    )
                }
            }
        }
    }
}

@Composable
fun PresentList(
    uiState: DrinksUiState,
    scaffoldState: ScaffoldState,
    onSearch: (String) -> Unit,
    onErrorDismissed: () -> Unit

) {
    Scaffold(scaffoldState = scaffoldState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            SearchBar(onSearch)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = uiState.drinks, key = { drink -> drink.id }) {
                    ListItem(it.name, it.ingredients, it.thumbnailUrl)
                }
            }
        }
    }

    if (uiState.errorMessageId > -1) {
        val errorMessageText = stringResource(id = uiState.errorMessageId)
        val retryMessageText = stringResource(R.string.retry)

        val onSearchState by rememberUpdatedState(onSearch)
        val onErrorDismissState by rememberUpdatedState(onErrorDismissed)

        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onSearchState(uiState.query)
            }
            // Once the message is displayed and dismissed, notify the ViewModel
            onErrorDismissState()
        }
    }
}

@Composable
fun SearchBar(onClick: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        val text = rememberSaveable { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 6.dp),
            value = text.value,
            onValueChange = { text.value = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,

            ),
            keyboardActions = KeyboardActions(onSearch = {
                onClick(text.value)
                focusManager.clearFocus()
            })
        )
        IconButton(
            onClick = { onClick(text.value) },
            enabled = text.value.isNotEmpty()
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = ""
            )
        }
    }
}

@Composable
fun ListItem(name: String, ingredients: List<String>, imageUrl: String) {
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
                data = imageUrl,
                placeHolderDrawable = AppCompatResources.getDrawable(
                    LocalContext.current,
                    R.drawable.ic_launcher_foreground
                ),
                glideModifier = { requestBuilder ->
                    requestBuilder.centerCrop()
                }
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = name,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = ingredients.joinToString(),
                    color = MaterialTheme.colors.onSurface,
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
        PresentList(
            DrinksUiState(isLoading = false, errorMessageId = -1, drinks = PreviewUtils.drinksList),
            rememberScaffoldState(),
            {},
            {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemPreview() {
    MyCocktailBarTheme {
        ListItem("Margarita", PreviewUtils.ingredients, "")
    }
}
