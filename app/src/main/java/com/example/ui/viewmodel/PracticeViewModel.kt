package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.PracticeSession
import com.example.data.local.entity.QuestionResult
import com.example.data.preferences.UserPreferencesRepository
import com.example.data.repository.SpeedMathRepository
import com.example.domain.generator.SpeedMathGeneratorEngine
import com.example.domain.generator.TopicsCatalog
import com.example.domain.model.Difficulty
import com.example.domain.model.GeneratedQuestion
import com.example.domain.model.PracticeConfig
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

sealed class AnswerFeedback {
    object Idle : AnswerFeedback()
    object Correct : AnswerFeedback()
    class Wrong(val correctAnswer: String) : AnswerFeedback()
}

data class PracticeState(
    val config: PracticeConfig,
    val questions: List<GeneratedQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val userAnswer: String = "",
    val correctCount: Int = 0,
    val wrongCount: Int = 0,
    val score: Int = 0,
    val currentSessionStreak: Int = 0,
    val bestSessionStreak: Int = 0,
    val remainingTimeSeconds: Int = 0,
    val elapsedTimeMillis: Long = 0,
    val isFinished: Boolean = false,
    val finishedSessionId: Long? = null,
    val feedback: AnswerFeedback = AnswerFeedback.Idle,
    val resultsList: List<QuestionResult> = emptyList()
)

class PracticeViewModel(
    private val repository: SpeedMathRepository,
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val generatorEngine = SpeedMathGeneratorEngine()

    private val _uiState = MutableStateFlow<PracticeState?>(null)
    val uiState: StateFlow<PracticeState?> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var questionStartTimeMillis: Long = System.currentTimeMillis()

    fun startPractice(
        topicId: String,
        difficultyStr: String,
        questionCount: Int,
        timeLimitSeconds: Int,
        isSpeedChallenge: Boolean = false,
        isDailyChallenge: Boolean = false
    ) {
        viewModelScope.launch {
            val topic = TopicsCatalog.getTopicById(topicId)
            val difficulty = try {
                Difficulty.valueOf(difficultyStr.uppercase())
            } catch (e: Exception) {
                Difficulty.EASY
            }

            val config = PracticeConfig(
                topic = topic,
                difficulty = difficulty,
                questionCount = if (isSpeedChallenge) 100 else questionCount,
                timeLimitSeconds = if (isSpeedChallenge) 60 else timeLimitSeconds,
                isSpeedChallenge = isSpeedChallenge,
                isDailyChallenge = isDailyChallenge
            )

            val questions = when {
                isSpeedChallenge -> generatorEngine.generate60sSpeedChallenge()
                isDailyChallenge -> generatorEngine.generateDailyChallenge().second
                else -> generatorEngine.generateQuestions(topicId, difficulty, questionCount)
            }

            _uiState.value = PracticeState(
                config = config,
                questions = questions,
                remainingTimeSeconds = config.timeLimitSeconds
            )

            questionStartTimeMillis = System.currentTimeMillis()
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(100)
                val state = _uiState.value ?: break
                if (state.isFinished) break

                val newElapsed = state.elapsedTimeMillis + 100
                var newRemaining = state.remainingTimeSeconds

                if (state.config.timeLimitSeconds > 0) {
                    val remainingMs = (state.config.timeLimitSeconds * 1000L) - newElapsed
                    newRemaining = maxOf(0, (remainingMs / 1000).toInt())

                    if (remainingMs <= 0) {
                        _uiState.value = state.copy(
                            remainingTimeSeconds = 0,
                            elapsedTimeMillis = state.config.timeLimitSeconds * 1000L
                        )
                        finishPracticeSession()
                        break
                    }
                }

                _uiState.value = state.copy(
                    elapsedTimeMillis = newElapsed,
                    remainingTimeSeconds = newRemaining
                )
            }
        }
    }

    fun onKeyInput(key: String) {
        val state = _uiState.value ?: return
        if (state.isFinished || state.feedback !is AnswerFeedback.Idle) return

        val currentAnswer = state.userAnswer
        val updatedAnswer = when (key) {
            "CLEAR" -> ""
            "DEL" -> if (currentAnswer.isNotEmpty()) currentAnswer.dropLast(1) else ""
            "-" -> if (currentAnswer.isEmpty()) "-" else currentAnswer
            "." -> if (!currentAnswer.contains(".")) currentAnswer + "." else currentAnswer
            else -> currentAnswer + key
        }

        _uiState.value = state.copy(userAnswer = updatedAnswer)
    }

    fun submitAnswer() {
        val state = _uiState.value ?: return
        if (state.isFinished || state.feedback !is AnswerFeedback.Idle) return

        val currentQuestion = state.questions.getOrNull(state.currentIndex) ?: return
        val userAnswerTrimmed = state.userAnswer.trim()
        
        if (userAnswerTrimmed.isEmpty()) return

        val responseTime = System.currentTimeMillis() - questionStartTimeMillis
        
        // Exact matching logic
        val isCorrect = checkAnswerCorrectness(userAnswerTrimmed, currentQuestion)

        val updatedCorrect = if (isCorrect) state.correctCount + 1 else state.correctCount
        val updatedWrong = if (!isCorrect) state.wrongCount + 1 else state.wrongCount
        val newStreak = if (isCorrect) state.currentSessionStreak + 1 else 0
        val bestStreak = maxOf(state.bestSessionStreak, newStreak)

        // Speed bonus
        val speedBonus = if (isCorrect && responseTime < 3000) 5 else 0
        val questionPoints = if (isCorrect) 10 + speedBonus else 0
        val updatedScore = state.score + questionPoints

        val questionResult = QuestionResult(
            sessionId = 0,
            questionText = currentQuestion.questionText,
            formattedExpression = currentQuestion.formattedExpression,
            correctAnswer = currentQuestion.correctAnswer,
            userAnswer = userAnswerTrimmed,
            isCorrect = isCorrect,
            responseTimeMillis = responseTime,
            explanation = currentQuestion.explanation
        )

        val updatedResults = state.resultsList + questionResult
        val feedback = if (isCorrect) AnswerFeedback.Correct else AnswerFeedback.Wrong(currentQuestion.correctAnswer)

        _uiState.value = state.copy(
            correctCount = updatedCorrect,
            wrongCount = updatedWrong,
            score = updatedScore,
            currentSessionStreak = newStreak,
            bestSessionStreak = bestStreak,
            feedback = feedback,
            resultsList = updatedResults
        )

        viewModelScope.launch {
            val settings = preferencesRepository.userSettingsFlow.first()
            val delayMs = if (settings.autoNextEnabled) 300L else 700L
            delay(delayMs)
            moveToNextQuestion()
        }
    }

    fun skipQuestion() {
        val state = _uiState.value ?: return
        if (state.isFinished || state.feedback !is AnswerFeedback.Idle) return

        val currentQuestion = state.questions.getOrNull(state.currentIndex) ?: return
        val responseTime = System.currentTimeMillis() - questionStartTimeMillis

        val questionResult = QuestionResult(
            sessionId = 0,
            questionText = currentQuestion.questionText,
            formattedExpression = currentQuestion.formattedExpression,
            correctAnswer = currentQuestion.correctAnswer,
            userAnswer = "Skipped",
            isCorrect = false,
            responseTimeMillis = responseTime,
            explanation = currentQuestion.explanation
        )

        _uiState.value = state.copy(
            wrongCount = state.wrongCount + 1,
            currentSessionStreak = 0,
            resultsList = state.resultsList + questionResult
        )

        moveToNextQuestion()
    }

    private fun checkAnswerCorrectness(userAnswer: String, question: GeneratedQuestion): Boolean {
        val target = question.correctAnswer.trim()
        if (userAnswer.equals(target, ignoreCase = true)) return true

        // Try double tolerance
        val userDbl = userAnswer.toDoubleOrNull()
        val targetDbl = target.toDoubleOrNull()
        if (userDbl != null && targetDbl != null) {
            return kotlin.math.abs(userDbl - targetDbl) < 0.01
        }
        return false
    }

    private fun moveToNextQuestion() {
        val state = _uiState.value ?: return
        val nextIndex = state.currentIndex + 1

        if (nextIndex >= state.questions.size) {
            finishPracticeSession()
        } else {
            questionStartTimeMillis = System.currentTimeMillis()
            _uiState.value = state.copy(
                currentIndex = nextIndex,
                userAnswer = "",
                feedback = AnswerFeedback.Idle
            )
        }
    }

    private fun finishPracticeSession() {
        val state = _uiState.value ?: return
        if (state.isFinished) return

        timerJob?.cancel()

        viewModelScope.launch {
            val totalQuestions = state.resultsList.size
            val correctCount = state.correctCount
            val wrongCount = state.wrongCount
            val totalTimeMs = state.elapsedTimeMillis
            val avgTimeMs = if (totalQuestions > 0) totalTimeMs / totalQuestions else 0L

            val session = PracticeSession(
                categoryName = state.config.topic.categoryGroup.title,
                topicId = state.config.topic.id,
                topicName = state.config.topic.name,
                difficulty = state.config.difficulty.displayName,
                totalQuestions = totalQuestions,
                correctAnswers = correctCount,
                wrongAnswers = wrongCount,
                totalTimeMillis = totalTimeMs,
                averageTimeMillis = avgTimeMs,
                score = state.score,
                isDailyChallenge = state.config.isDailyChallenge,
                isSpeedChallenge = state.config.isSpeedChallenge
            )

            val sessionId = repository.saveSession(session, state.resultsList)

            _uiState.value = state.copy(
                isFinished = true,
                finishedSessionId = sessionId
            )
        }
    }
}
