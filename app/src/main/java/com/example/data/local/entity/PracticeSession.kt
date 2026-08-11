package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_sessions")
data class PracticeSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val categoryName: String,
    val topicId: String,
    val topicName: String,
    val difficulty: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val totalTimeMillis: Long,
    val averageTimeMillis: Long,
    val score: Int,
    val streakAtSession: Int = 0,
    val isDailyChallenge: Boolean = false,
    val isSpeedChallenge: Boolean = false
)
