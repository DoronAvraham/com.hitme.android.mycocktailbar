package com.hitme.android.mycocktailbar.ui.compose

import com.hitme.android.mycocktailbar.data.Cocktail

/**
 * Generate different data objects for easy usage in Compose Preview.
 */
object PreviewUtils {

    val drinksList: List<Cocktail>
        get() {
            val list = mutableListOf<Cocktail>()
            for (count in 1..20) {
                list.add(
                    Cocktail(
                        id = count.toString(),
                        name = "Drink$count",
                        ingredients = listOf("Sugar", "Salt", "Ice", "Lime")
                    )
                )
            }
            return list
        }
}
