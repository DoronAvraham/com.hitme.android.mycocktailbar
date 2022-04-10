package com.hitme.android.mycocktailbar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyCocktailBarTheme(darkTheme = true) {
                MyCocktailBarApp()
            }
        }
    }
}
