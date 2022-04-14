package com.hitme.android.mycocktailbar.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "cocktails")
@Serializable
data class Cocktail(
    @PrimaryKey
    @SerialName("idDrink")
    val id: String = "",
    @SerialName("strDrink")
    val name: String = "",
    @SerialName("ingredients")
    val ingredients: List<String> = emptyList(),
    @SerialName("measures")
    val measures: List<String> = emptyList(),
    @SerialName("strInstructions")
    val instructions: String = "",
    @SerialName("strDrinkThumb")
    val thumbnailUrl: String = "",
    @SerialName("strImageSource")
    val imageUrl: String = ""
)
