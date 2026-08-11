package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.preferences.UserPreferencesRepository
import com.example.data.preferences.UserSettings
import com.example.data.repository.SpeedMathRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val preferencesRepository: UserPreferencesRepository,
    private val repository: SpeedMathRepository
) : ViewModel() {

    val settings: StateFlow<UserSettings> = preferencesRepository.userSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings()
        )

    fun toggleSound(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateSoundEnabled(enabled)
        }
    }

    fun toggleVibration(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateVibrationEnabled(enabled)
        }
    }

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            preferencesRepository.updateThemeMode(mode)
        }
    }

    fun toggleTimer(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateTimerEnabled(enabled)
        }
    }

    fun toggleAutoNext(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateAutoNextEnabled(enabled)
        }
    }

    fun resetAllStatistics() {
        viewModelScope.launch {
            repository.clearAllData()
        }
    }
}
