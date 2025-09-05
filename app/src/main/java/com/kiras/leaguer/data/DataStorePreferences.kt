package com.kiras.leaguer.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kiras.leaguer.domain.repository.SettingsRepos
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "APP_PREFERENCES"
)

class DataStorePreferences(private val context: Context): SettingsRepos {

    private object PreferencesKey {
        val passwordDataKey = stringPreferencesKey(name = "PASSWORD_DATA_KEY")
    }

    override suspend fun writeSettingsDataAsJson(string: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKey.passwordDataKey] = string
        }
    }

    override suspend fun readSettingsDataAsJson(): String? {
        try {
            val preferences = context.dataStore.data.first()
            return preferences[PreferencesKey.passwordDataKey]
        } catch (_: NoSuchElementException) {
            return null
        }
    }
}