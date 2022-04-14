package com.hitme.android.mycocktailbar.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.hitme.android.mycocktailbar.ui.theme.MyCocktailBarTheme

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = color)
    }
}

@Preview(showBackground = false)
@Composable
private fun PreviewCircularProgressBar() {
    MyCocktailBarTheme(darkTheme = false) {
        CircularProgressBar()
    }
}
