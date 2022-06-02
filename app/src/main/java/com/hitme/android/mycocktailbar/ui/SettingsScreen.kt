package com.hitme.android.mycocktailbar.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hitme.android.mycocktailbar.DataStoreManager
import com.hitme.android.mycocktailbar.R
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(dataStoreManager: DataStoreManager = DataStoreManager(LocalContext.current)) {
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1f), text = stringResource(R.string.dark_mode))
        Switch(
            checked = dataStoreManager.darkMode.collectAsState(initial = false).value,
            onCheckedChange = { enabled -> scope.launch { dataStoreManager.setDarkMode(enabled) } }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MyCocktailBarTheme {
        SettingsScreen()
    }
}
