package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.entity.PracticeSession
import com.example.data.local.entity.QuestionResult
import com.example.data.repository.SpeedMathRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ResultUiState(
    val isLoading: Boolean = true,
    val session: PracticeSession? = null,
    val questionResults: List<QuestionResult> = emptyList()
)

class ResultViewModel(private val repository: SpeedMathRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState

    fun loadResult(sessionId: Long) {
        viewModelScope.launch {
            _uiState.value = ResultUiState(isLoading = true)
            val session = repository.getSessionDetails(sessionId)
            val results = repository.getQuestionResultsForSession(sessionId)
            _uiState.value = ResultUiState(
                isLoading = false,
                session = session,
                questionResults = results
            )
        }
    }
}
