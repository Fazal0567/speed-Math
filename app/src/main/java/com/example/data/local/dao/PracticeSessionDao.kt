package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.PracticeSession
import com.example.data.local.entity.QuestionResult
import kotlinx.coroutines.flow.Flow

data class TopicStat(
    val topicId: String,
    val topicName: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val bestScore: Int,
    val fastestAvgTimeMillis: Long
)

data class DailyActivity(
    val dayEpoch: Long,
    val totalQuestions: Int,
    val totalCorrect: Int
)

@Dao
interface PracticeSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: PracticeSession): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionResults(results: List<QuestionResult>)

    @Query("SELECT * FROM practice_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<PracticeSession>>

    @Query("SELECT * FROM practice_sessions ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentSessions(limit: Int = 10): Flow<List<PracticeSession>>

    @Query("SELECT * FROM practice_sessions WHERE id = :sessionId LIMIT 1")
    suspend fun getSessionById(sessionId: Long): PracticeSession?

    @Query("SELECT * FROM question_results WHERE sessionId = :sessionId")
    suspend fun getQuestionsForSession(sessionId: Long): List<QuestionResult>

    @Query("""
        SELECT 
            topicId, 
            topicName, 
            SUM(totalQuestions) as totalQuestions, 
            SUM(correctAnswers) as correctAnswers, 
            MAX(score) as bestScore, 
            MIN(averageTimeMillis) as fastestAvgTimeMillis 
        FROM practice_sessions 
        GROUP BY topicId
    """)
    fun getTopicStats(): Flow<List<TopicStat>>

    @Query("SELECT * FROM practice_sessions WHERE isDailyChallenge = 1 AND timestamp >= :startOfDayTimestamp LIMIT 1")
    suspend fun getTodayDailyChallengeSession(startOfDayTimestamp: Long): PracticeSession?

    @Query("DELETE FROM practice_sessions")
    suspend fun clearAllSessions()

    @Query("DELETE FROM question_results")
    suspend fun clearAllQuestionResults()

    @Transaction
    suspend fun clearAllData() {
        clearAllQuestionResults()
        clearAllSessions()
    }
}
