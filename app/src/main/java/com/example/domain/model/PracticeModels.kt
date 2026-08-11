package com.example.domain.model

enum class Difficulty(val displayName: String, val description: String) {
    EASY("Easy", "Simple numbers & fast calculations"),
    MEDIUM("Medium", "Larger numbers & multi-step math"),
    HARD("Hard", "Competitive exam standard questions"),
    MIXED("Mixed", "Random combination of all difficulties")
}

enum class CategoryGroup(val id: String, val title: String, val iconName: String) {
    BASIC_CALCULATION("basic", "Basic Calculation", "Functions"),
    NUMBER_SKILLS("number", "Number Skills", "Pin"),
    ARITHMETIC("arithmetic", "Arithmetic", "Calculate"),
    ADVANCED("advanced", "Advanced Math", "Psychology")
}

data class Topic(
    val id: String,
    val name: String,
    val categoryGroup: CategoryGroup,
    val description: String,
    val iconName: String = "Calculate"
)

data class GeneratedQuestion(
    val id: Int,
    val questionText: String,
    val formattedExpression: String,
    val correctAnswer: String,
    val numericAnswer: Double,
    val options: List<String> = emptyList(),
    val explanation: String = ""
)

data class PracticeConfig(
    val topic: Topic,
    val difficulty: Difficulty = Difficulty.EASY,
    val questionCount: Int = 20, // 10, 20, 30, 50, 100
    val timeLimitSeconds: Int = 120, // 60, 120, 300, 600, 0 (no limit)
    val isSpeedChallenge: Boolean = false,
    val isDailyChallenge: Boolean = false
)
