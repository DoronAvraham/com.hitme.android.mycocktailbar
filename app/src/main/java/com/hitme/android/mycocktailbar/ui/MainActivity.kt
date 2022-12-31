package com.hitme.android.mycocktailbar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hitme.android.mycocktailbar.DataStoreManager
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkMode = dataStoreManager.darkMode.collectAsStateWithLifecycle(false)
            MyCocktailBarTheme(darkTheme = isDarkMode.value) {
                MyCocktailBarApp(dataStoreManager)
            }
        }
    }
}
