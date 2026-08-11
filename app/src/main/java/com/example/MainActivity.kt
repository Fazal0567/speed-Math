package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ui.navigation.Screen
import com.example.ui.screens.ChooseConfigScreen
import com.example.ui.screens.ChooseTopicScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.QuestionPracticeScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.screens.RevisionScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StatisticsScreen
import com.example.ui.screens.ViewAnswersScreen
import com.example.ui.theme.SpeedMathTheme
import com.example.ui.viewmodel.HomeViewModel
import com.example.ui.viewmodel.PracticeViewModel
import com.example.ui.viewmodel.ResultViewModel
import com.example.ui.viewmodel.SettingsViewModel
import com.example.ui.viewmodel.SpeedMathViewModelFactory
import com.example.ui.viewmodel.StatisticsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpeedMathTheme {
                SpeedMathApp(this.application)
            }
        }
    }
}

@Composable
fun SpeedMathApp(application: android.app.Application) {
    val navController = rememberNavController()
    val factory = SpeedMathViewModelFactory(application)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = viewModel(factory = factory)
            HomeScreen(
                viewModel = homeViewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.ChooseTopic.route) {
            ChooseTopicScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(
            route = Screen.ChooseConfig.route,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "addition"
            ChooseConfigScreen(
                topicId = topicId,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(
            route = Screen.Practice.route,
            arguments = listOf(
                navArgument("topicId") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("questionCount") { type = NavType.IntType },
                navArgument("timeSeconds") { type = NavType.IntType },
                navArgument("isSpeed") { type = NavType.BoolType; defaultValue = false },
                navArgument("isDaily") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId") ?: "addition"
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "easy"
            val questionCount = backStackEntry.arguments?.getInt("questionCount") ?: 20
            val timeSeconds = backStackEntry.arguments?.getInt("timeSeconds") ?: 120
            val isSpeed = backStackEntry.arguments?.getBoolean("isSpeed") ?: false
            val isDaily = backStackEntry.arguments?.getBoolean("isDaily") ?: false

            val practiceViewModel: PracticeViewModel = viewModel(factory = factory)
            QuestionPracticeScreen(
                topicId = topicId,
                difficulty = difficulty,
                questionCount = questionCount,
                timeSeconds = timeSeconds,
                isSpeed = isSpeed,
                isDaily = isDaily,
                viewModel = practiceViewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(navArgument("sessionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: 0L
            val resultViewModel: ResultViewModel = viewModel(factory = factory)
            ResultScreen(
                sessionId = sessionId,
                viewModel = resultViewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(
            route = Screen.ViewAnswers.route,
            arguments = listOf(navArgument("sessionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: 0L
            val resultViewModel: ResultViewModel = viewModel(factory = factory)
            ViewAnswersScreen(
                sessionId = sessionId,
                viewModel = resultViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Statistics.route) {
            val statsViewModel: StatisticsViewModel = viewModel(factory = factory)
            StatisticsScreen(
                viewModel = statsViewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Revision.route) {
            RevisionScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Settings.route) {
            val settingsViewModel: SettingsViewModel = viewModel(factory = factory)
            SettingsScreen(
                viewModel = settingsViewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    }
}
