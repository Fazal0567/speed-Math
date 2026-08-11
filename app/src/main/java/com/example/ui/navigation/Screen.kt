package com.example.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ChooseTopic : Screen("choose_topic")
    object ChooseConfig : Screen("choose_config/{topicId}") {
        fun createRoute(topicId: String) = "choose_config/$topicId"
    }
    object Practice : Screen("practice/{topicId}/{difficulty}/{questionCount}/{timeSeconds}/{isSpeed}/{isDaily}") {
        fun createRoute(
            topicId: String,
            difficulty: String,
            questionCount: Int,
            timeSeconds: Int,
            isSpeed: Boolean = false,
            isDaily: Boolean = false
        ) = "practice/$topicId/$difficulty/$questionCount/$timeSeconds/$isSpeed/$isDaily"
    }
    object Result : Screen("result/{sessionId}") {
        fun createRoute(sessionId: Long) = "result/$sessionId"
    }
    object ViewAnswers : Screen("view_answers/{sessionId}") {
        fun createRoute(sessionId: Long) = "view_answers/$sessionId"
    }
    object Statistics : Screen("statistics")
    object Revision : Screen("revision")
    object Settings : Screen("settings")
    object DailyChallenge : Screen("daily_challenge")
    object SpeedChallenge : Screen("speed_challenge")
}
