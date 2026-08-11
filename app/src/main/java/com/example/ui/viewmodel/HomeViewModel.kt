package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.PracticeSession
import com.example.data.local.entity.UserStats
import com.example.data.repository.SpeedMathRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class TodayStats(
    val attempted: Int = 0,
    val correct: Int = 0,
    val accuracy: Float = 0f,
    val avgTimeSec: Float = 0f,
    val bestSpeed: Int = 0
)

class HomeViewModel(private val repository: SpeedMathRepository) : ViewModel() {

    val userStats: StateFlow<UserStats> = repository.userStats
        .map { it ?: UserStats() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserStats()
        )

    val recentSessions: StateFlow<List<PracticeSession>> = repository.recentSessions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val todayStats: StateFlow<TodayStats> = repository.allSessions.map { sessions ->
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val todaySessions = sessions.filter { it.timestamp >= startOfDay }

        val attempted = todaySessions.sumOf { it.totalQuestions }
        val correct = todaySessions.sumOf { it.correctAnswers }
        val accuracy = if (attempted > 0) (correct.toFloat() / attempted) * 100f else 0f
        
        val totalTimeMs = todaySessions.sumOf { it.totalTimeMillis }
        val avgTimeSec = if (attempted > 0) (totalTimeMs.toFloat() / 1000f) / attempted else 0f

        val bestSpeed = todaySessions.maxOfOrNull { session ->
            if (session.totalTimeMillis > 0) {
                ((session.totalQuestions.toFloat() / (session.totalTimeMillis.toFloat() / 60000f))).toInt()
            } else 0
        } ?: 0

        TodayStats(
            attempted = attempted,
            correct = correct,
            accuracy = accuracy,
            avgTimeSec = avgTimeSec,
            bestSpeed = bestSpeed
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TodayStats()
    )
}
