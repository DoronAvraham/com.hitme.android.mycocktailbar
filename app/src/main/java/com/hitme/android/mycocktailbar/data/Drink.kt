package com.hitme.android.mycocktailbar.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    @SerialName("idDrink")
    val id: String = "",
    @SerialName("strDrink")
    val name: String = "",
    @SerialName("ingredients")
    val ingredients: List<String> = listOf(),
    @SerialName("measures")
    val measures: List<String> = listOf(),
    @SerialName("strInstructions")
    val instructions: String = "",
    @SerialName("strDrinkThumb")
    val thumbnailUrl: String = "",
    @SerialName("strImageSource")
    val imageUrl: String = ""
)
