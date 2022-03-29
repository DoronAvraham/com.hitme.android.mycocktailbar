package com.hitme.android.mycocktailbar

import com.hitme.android.mycocktailbar.data.Drink

/**
 * Generate different data objects for easy usage in Compose Preview.
 */
object PreviewUtils {

    val ingredients = listOf("Sugar", "Salt", "Ice", "Lime")

    val drinksList: List<Drink>
        get() {
            val list = mutableListOf<Drink>()
            for (count in 1..20) {
                list.add(Drink(id = count.toString(), name = "Drink$count", ingredients = ingredients))
            }
            return list
        }
}
