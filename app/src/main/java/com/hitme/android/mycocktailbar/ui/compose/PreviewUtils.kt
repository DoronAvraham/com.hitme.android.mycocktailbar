package com.hitme.android.mycocktailbar.ui.compose

import com.hitme.android.mycocktailbar.data.Cocktail

/**
 * Generate different data objects for easy usage in Compose Preview.
 */
object PreviewUtils {

    val cocktail: Cocktail = Cocktail(
        id = "100",
        name = "Preview Cocktail",
        instructions = "Mix all and drink",
        ingredients = listOf("Sugar", "Salt", "Ice", "Lime")
    )

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
