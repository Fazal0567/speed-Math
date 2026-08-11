package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_results",
    foreignKeys = [
        ForeignKey(
            entity = PracticeSession::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["sessionId"])]
)
data class QuestionResult(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sessionId: Long,
    val questionText: String,
    val formattedExpression: String,
    val correctAnswer: String,
    val userAnswer: String,
    val isCorrect: Boolean,
    val responseTimeMillis: Long,
    val explanation: String = ""
)
