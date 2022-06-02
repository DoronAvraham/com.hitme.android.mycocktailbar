package com.hitme.android.mycocktailbar

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    suspend fun setDarkMode(darkMode: Boolean) {
        context.appPrefs.edit { it[DARK_MODE_KEY] = darkMode }
    }

    val darkMode: Flow<Boolean>
        get() = context.appPrefs.data.map { it[DARK_MODE_KEY] ?: true }

    companion object {
        private const val DATASTORE_NAME = "app_preferences"

        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_key");

        private val Context.appPrefs by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }
}