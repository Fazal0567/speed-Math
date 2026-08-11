package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.navigation.Screen
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.GreenCorrect
import com.example.ui.theme.RedWrong
import com.example.ui.viewmodel.AnswerFeedback
import com.example.ui.viewmodel.PracticeViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionPracticeScreen(
    topicId: String,
    difficulty: String,
    questionCount: Int,
    timeSeconds: Int,
    isSpeed: Boolean,
    isDaily: Boolean,
    viewModel: PracticeViewModel,
    onNavigate: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(topicId, difficulty, questionCount, timeSeconds, isSpeed, isDaily) {
        if (state == null) {
            viewModel.startPractice(
                topicId = topicId,
                difficultyStr = difficulty,
                questionCount = questionCount,
                timeLimitSeconds = timeSeconds,
                isSpeedChallenge = isSpeed,
                isDailyChallenge = isDaily
            )
        }
    }

    LaunchedEffect(state?.isFinished, state?.finishedSessionId) {
        val sid = state?.finishedSessionId
        if (state?.isFinished == true && sid != null) {
            onNavigate(Screen.Result.createRoute(sid))
        }
    }

    val practiceState = state ?: return

    val currentQuestion = practiceState.questions.getOrNull(practiceState.currentIndex)
    val totalCount = practiceState.questions.size
    val currentNumber = practiceState.currentIndex + 1

    val minutes = practiceState.remainingTimeSeconds / 60
    val seconds = practiceState.remainingTimeSeconds % 60
    val timerText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)

    val feedbackColor = when (practiceState.feedback) {
        is AnswerFeedback.Correct -> GreenCorrect
        is AnswerFeedback.Wrong -> RedWrong
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Q $currentNumber / $totalCount",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )

                        // Timer Badge
                        if (practiceState.config.timeLimitSeconds > 0) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        if (practiceState.remainingTimeSeconds < 15) RedWrong.copy(alpha = 0.2f)
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                                    .testTag("timer_badge")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Timer,
                                    contentDescription = "Timer",
                                    tint = if (practiceState.remainingTimeSeconds < 15) RedWrong else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = timerText,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = if (practiceState.remainingTimeSeconds < 15) RedWrong else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Score & Live Streak
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (practiceState.currentSessionStreak >= 2) {
                                Icon(
                                    imageVector = Icons.Filled.LocalFireDepartment,
                                    contentDescription = "Streak",
                                    tint = AmberStreak,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "${practiceState.currentSessionStreak}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = AmberStreak
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                text = "${practiceState.score} pts",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigate(Screen.Home.route) }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Exit")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Progress Bar
            LinearProgressIndicator(
                progress = { if (totalCount > 0) currentNumber.toFloat() / totalCount else 0f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Score Counter Bar (Correct / Wrong)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Correct: ${practiceState.correctCount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = GreenCorrect
                )
                Text(
                    text = "Wrong: ${practiceState.wrongCount}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = RedWrong
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Big Question Card
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 3.dp,
                    color = feedbackColor
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("question_card")
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentQuestion != null) {
                        Text(
                            text = currentQuestion.questionText,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentQuestion.formattedExpression,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.testTag("text_expression")
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Typed Answer Box
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    width = 2.dp,
                                    color = if (practiceState.userAnswer.isNotEmpty()) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (practiceState.userAnswer.isEmpty()) "Type answer..." else practiceState.userAnswer,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                color = if (practiceState.userAnswer.isEmpty()) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.testTag("text_user_answer")
                            )
                        }

                        // Immediate Feedback Indicator
                        AnimatedVisibility(
                            visible = practiceState.feedback !is AnswerFeedback.Idle,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            when (val fb = practiceState.feedback) {
                                is AnswerFeedback.Correct -> {
                                    Text(
                                        text = "✓ CORRECT!",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp,
                                        color = GreenCorrect
                                    )
                                }
                                is AnswerFeedback.Wrong -> {
                                    Text(
                                        text = "✗ WRONG! Answer: ${fb.correctAnswer}",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 14.sp,
                                        color = RedWrong
                                    )
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Numeric Keypad Grid
            NumericKeypad(
                onKeyClick = { key -> viewModel.onKeyInput(key) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Submit & Skip Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { viewModel.skipQuestion() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("btn_skip")
                ) {
                    Icon(
                        imageVector = Icons.Filled.SkipNext,
                        contentDescription = "Skip",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("SKIP", color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { viewModel.submitAnswer() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    enabled = practiceState.userAnswer.isNotEmpty() && practiceState.feedback is AnswerFeedback.Idle,
                    modifier = Modifier
                        .weight(2f)
                        .height(50.dp)
                        .testTag("btn_submit")
                ) {
                    Icon(imageVector = Icons.Filled.Send, contentDescription = "Submit")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("SUBMIT", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun NumericKeypad(
    onKeyClick: (String) -> Unit
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("-", "0", "CLEAR")
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        keys.forEach { rowKeys ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowKeys.forEach { key ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (key == "CLEAR") MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .clickable { onKeyClick(key) }
                            .testTag("key_$key")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = key,
                                fontSize = if (key == "CLEAR") 14.sp else 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (key == "CLEAR") MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
