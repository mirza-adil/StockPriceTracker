package com.mirza.stockpricetracker.data.preferences

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.themePreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "theme_preferences"
)

class ThemePreferenceStore(context: Context) {

    private val dataStore = context.applicationContext.themePreferencesDataStore

    suspend fun resolveDark(systemIsDark: Boolean): Boolean {
        val prefs = dataStore.data.first()
        return if (prefs[KEY_USER_SET] == true) {
            prefs[KEY_USE_DARK] == true
        } else {
            systemIsDark
        }
    }

    suspend fun setUserDarkTheme(useDark: Boolean) {
        dataStore.edit { prefs ->
            prefs[KEY_USER_SET] = true
            prefs[KEY_USE_DARK] = useDark
        }
    }

    /**
     * Applies saved preference to the process-wide night mode so [androidx.compose.foundation.isSystemInDarkTheme]
     * and resources match (DayNight theme + config uiMode).
     */
    suspend fun applyStoredNightModeToDelegate() {
        val prefs = dataStore.data.first()
        val mode = when {
            prefs[KEY_USER_SET] != true -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            prefs[KEY_USE_DARK] == true -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private companion object {
        val KEY_USER_SET = booleanPreferencesKey("user_set_theme")
        val KEY_USE_DARK = booleanPreferencesKey("use_dark_theme")
    }
}
