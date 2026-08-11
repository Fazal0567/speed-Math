package com.example.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "speed_math_settings")

data class UserSettings(
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val themeMode: String = "SYSTEM", // "SYSTEM", "LIGHT", "DARK"
    val timerEnabled: Boolean = true,
    val autoNextEnabled: Boolean = true
)

class UserPreferencesRepository(private val context: Context) {

    private object PreferencesKeys {
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val VIBRATION_ENABLED = booleanPreferencesKey("vibration_enabled")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val TIMER_ENABLED = booleanPreferencesKey("timer_enabled")
        val AUTO_NEXT_ENABLED = booleanPreferencesKey("auto_next_enabled")
    }

    val userSettingsFlow: Flow<UserSettings> = context.dataStore.data.map { preferences ->
        UserSettings(
            soundEnabled = preferences[PreferencesKeys.SOUND_ENABLED] ?: true,
            vibrationEnabled = preferences[PreferencesKeys.VIBRATION_ENABLED] ?: true,
            themeMode = preferences[PreferencesKeys.THEME_MODE] ?: "SYSTEM",
            timerEnabled = preferences[PreferencesKeys.TIMER_ENABLED] ?: true,
            autoNextEnabled = preferences[PreferencesKeys.AUTO_NEXT_ENABLED] ?: true
        )
    }

    suspend fun updateSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SOUND_ENABLED] = enabled
        }
    }

    suspend fun updateVibrationEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.VIBRATION_ENABLED] = enabled
        }
    }

    suspend fun updateThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = mode
        }
    }

    suspend fun updateTimerEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TIMER_ENABLED] = enabled
        }
    }

    suspend fun updateAutoNextEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTO_NEXT_ENABLED] = enabled
        }
    }
}
