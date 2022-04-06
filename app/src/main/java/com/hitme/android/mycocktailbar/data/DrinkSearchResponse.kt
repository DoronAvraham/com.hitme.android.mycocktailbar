package com.hitme.android.mycocktailbar.data

import com.hitme.android.mycocktailbar.json.DrinkTransformationSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkSearchResponse(
    @SerialName("drinks")
    val drinks: List<@Serializable(with = DrinkTransformationSerializer::class) Drink> = emptyList()
)
