package com.hitme.android.mycocktailbar.json

import com.hitme.android.mycocktailbar.data.Drink
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.JsonTransformingSerializer

/**
 * The returned Json contains "ingredients" and "measures" as separate parameters instead of a list.
 * This transformation serializer makes sure to convert them to lists before they are deserialized to [Drink].
 */
object DrinkTransformationSerializer : JsonTransformingSerializer<Drink>(Drink.serializer()) {

    override fun transformDeserialize(element: JsonElement): JsonElement {
        val ingredients = asList("strIngredient", element as JsonObject)
        val measures = asList("strMeasure", element)

        return JsonObject(element.toMutableMap().apply {
            put("ingredients", JsonArray(ingredients))
            put("measures", JsonArray(measures))
        })
    }

    private fun asList(prefix: String, jsonObject: JsonObject): List<JsonPrimitive> {
        val list = mutableListOf<JsonPrimitive>()
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