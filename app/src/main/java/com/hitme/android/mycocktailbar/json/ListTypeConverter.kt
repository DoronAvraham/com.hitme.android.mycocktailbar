package com.hitme.android.mycocktailbar.json

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Converts List<String> <--> Json in order to serialize lists into DB.
 */
class ListTypeConverter {

    @TypeConverter
    fun listToJson(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String): List<String> = Json.decodeFromString(value)
}
