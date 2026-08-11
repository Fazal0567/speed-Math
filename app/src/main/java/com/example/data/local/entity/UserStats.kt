package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_stats")
data class UserStats(
    @PrimaryKey val id: Int = 1,
    val totalQuestions: Int = 0,
    val totalCorrect: Int = 0,
    val totalWrong: Int = 0,
    val bestScore: Int = 0,
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val lastPracticeDateEpochDay: Long = 0L
)
