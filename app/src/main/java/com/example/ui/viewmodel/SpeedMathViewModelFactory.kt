package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.local.AppDatabase
import com.example.data.preferences.UserPreferencesRepository
import com.example.data.repository.SpeedMathRepository

class SpeedMathViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    private val database by lazy { AppDatabase.getDatabase(application) }
    private val repository by lazy {
        SpeedMathRepository(
            sessionDao = database.practiceSessionDao(),
            statsDao = database.userStatsDao()
        )
    }
    private val preferencesRepository by lazy {
        UserPreferencesRepository(application)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PracticeViewModel::class.java) -> {
                PracticeViewModel(repository, preferencesRepository) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(repository) as T
            }
            modelClass.isAssignableFrom(StatisticsViewModel::class.java) -> {
                StatisticsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(preferencesRepository, repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
