package com.hitme.android.mycocktailbar.json

import com.hitme.android.mycocktailbar.data.Cocktail
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer

/**
 * The returned Json contains "ingredients" and "measures" as separate parameters instead of a list.
 * This transformation serializer makes sure to convert them to lists before they are deserialized to [Cocktail].
 */
object DrinkTransformationSerializer : JsonTransformingSerializer<Cocktail>(Cocktail.serializer()) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        val ingredients = asList("strIngredient", element as JsonObject)
        val measures = asList("strMeasure", element)
        // Add padding to prevent IndexOutOfBoundException.
        while (ingredients.size > measures.size) {
            measures.add(JsonPrimitive(""))
        }

        return JsonObject(
            element.toMutableMap().apply {
                put("ingredients", JsonArray(ingredients))
                put("measures", JsonArray(measures))
            }
        )
    }

    private fun asList(prefix: String, jsonObject: JsonObject): MutableList<JsonPrimitive> {
        val list = mutableListOf<JsonPrimitive>()
        // There are max 15 items of each type.
        for (count in 1..15) {
            val item: String? = jsonObject["$prefix$count"]?.toString()
            if (item.equals("null") || item.isNullOrEmpty()) {
                break
            } else {
                list.add(JsonPrimitive(item.replace("\"", "")))
            }
        }
        return list
    }
}
