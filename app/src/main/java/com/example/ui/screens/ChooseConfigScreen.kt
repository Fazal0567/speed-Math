package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.generator.TopicsCatalog
import com.example.domain.model.Difficulty
import com.example.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseConfigScreen(
    topicId: String,
    onNavigate: (String) -> Unit
) {
    val topic = remember(topicId) { TopicsCatalog.getTopicById(topicId) }

    var selectedDifficulty by remember { mutableStateOf(Difficulty.MEDIUM) }
    var selectedQuestionCount by remember { mutableStateOf(20) }
    var selectedTimeSeconds by remember { mutableStateOf(120) } // 2 minutes

    val questionOptions = listOf(10, 20, 30, 50, 100)
    val timeOptions = listOf(
        Pair("1 Min", 60),
        Pair("2 Min", 120),
        Pair("5 Min", 300),
        Pair("10 Min", 600),
        Pair("No Timer", 0)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(topic.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { onNavigate(Screen.ChooseTopic.route) }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
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
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle Description
            Text(
                text = topic.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Difficulty Level Section
            Text(
                text = "Difficulty Level",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Difficulty.values().forEach { diff ->
                val isSelected = selectedDifficulty == diff
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedDifficulty = diff }
                        .testTag("difficulty_${diff.name.lowercase()}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = diff.displayName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = diff.description,
                            fontSize = 12.sp,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Number of Questions
            Text(
                text = "Number of Questions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                questionOptions.forEach { count ->
                    FilterChip(
                        selected = selectedQuestionCount == count,
                        onClick = { selectedQuestionCount = count },
                        label = { Text("$count Qs") },
                        modifier = Modifier.testTag("count_$count")
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Time Limit
            Text(
                text = "Time Limit",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                timeOptions.forEach { (label, seconds) ->
                    FilterChip(
                        selected = selectedTimeSeconds == seconds,
                        onClick = { selectedTimeSeconds = seconds },
                        label = { Text(label) },
                        modifier = Modifier.testTag("time_$seconds")
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Start Practice Button
            Button(
                onClick = {
                    onNavigate(
                        Screen.Practice.createRoute(
                            topicId = topic.id,
                            difficulty = selectedDifficulty.name.lowercase(),
                            questionCount = selectedQuestionCount,
                            timeSeconds = selectedTimeSeconds
                        )
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("btn_start_practice")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "START PRACTICE",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
