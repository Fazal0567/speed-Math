package com.example.data.repository

import com.example.data.local.dao.PracticeSessionDao
import com.example.data.local.dao.TopicStat
import com.example.data.local.dao.UserStatsDao
import com.example.data.local.entity.PracticeSession
import com.example.data.local.entity.QuestionResult
import com.example.data.local.entity.UserStats
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.concurrent.TimeUnit

class SpeedMathRepository(
    private val sessionDao: PracticeSessionDao,
    private val statsDao: UserStatsDao
) {

    val allSessions: Flow<List<PracticeSession>> = sessionDao.getAllSessions()
    val recentSessions: Flow<List<PracticeSession>> = sessionDao.getRecentSessions(10)
    val userStats: Flow<UserStats?> = statsDao.getUserStats()
    val topicStats: Flow<List<TopicStat>> = sessionDao.getTopicStats()

    suspend fun saveSession(
        session: PracticeSession,
        results: List<QuestionResult>
    ): Long {
        val todayEpochDay = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis())
        
        // 1. Get or create current user stats
        val currentStats = statsDao.getUserStatsOnce() ?: UserStats()

        // 2. Calculate streak
        val lastDay = currentStats.lastPracticeDateEpochDay
        val newStreak = when {
            lastDay == 0L -> 1 // First time
            lastDay == todayEpochDay -> currentStats.currentStreak // Already practiced today
            lastDay == todayEpochDay - 1 -> currentStats.currentStreak + 1 // Practiced yesterday
            else -> 1 // Streak broken
        }
        val updatedBestStreak = maxOf(currentStats.bestStreak, newStreak)

        val updatedTotalQuestions = currentStats.totalQuestions + session.totalQuestions
        val updatedTotalCorrect = currentStats.totalCorrect + session.correctAnswers
        val updatedTotalWrong = currentStats.totalWrong + session.wrongAnswers
        val updatedBestScore = maxOf(currentStats.bestScore, session.score)

        val updatedStats = currentStats.copy(
            totalQuestions = updatedTotalQuestions,
            totalCorrect = updatedTotalCorrect,
            totalWrong = updatedTotalWrong,
            bestScore = updatedBestScore,
            currentStreak = newStreak,
            bestStreak = updatedBestStreak,
            lastPracticeDateEpochDay = todayEpochDay
        )

        statsDao.insertOrUpdateUserStats(updatedStats)

        // 3. Save session and results
        val sessionWithStreak = session.copy(streakAtSession = newStreak)
        val sessionId = sessionDao.insertSession(sessionWithStreak)
        
        val resultsWithSessionId = results.map { it.copy(sessionId = sessionId) }
        sessionDao.insertQuestionResults(resultsWithSessionId)

        return sessionId
    }

    suspend fun getSessionDetails(sessionId: Long): PracticeSession? {
        return sessionDao.getSessionById(sessionId)
    }

    suspend fun getQuestionResultsForSession(sessionId: Long): List<QuestionResult> {
        return sessionDao.getQuestionsForSession(sessionId)
    }

    suspend fun hasCompletedDailyChallengeToday(): Boolean {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        return sessionDao.getTodayDailyChallengeSession(startOfDay) != null
    }

    suspend fun clearAllData() {
        sessionDao.clearAllData()
        statsDao.clearUserStats()
    }
}
