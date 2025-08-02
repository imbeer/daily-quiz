package com.toadthegod.dailyquiz.ui.screen.welcome

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toadthegod.dailyquiz.data.question.QuestionCache
import com.toadthegod.dailyquiz.data.question.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class WelcomeUiState(
    val isLoading: Boolean = false
)

sealed interface WelcomeEvent {
    data object NavigateToQuiz : WelcomeEvent
    data class ShowError(val message: String) : WelcomeEvent
}

class QuizStartViewModel(private val questionRepository: QuestionRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState = _uiState.asStateFlow()
    private val _eventFlow = MutableSharedFlow<WelcomeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
                val questions = withContext(Dispatchers.IO) {
                    questionRepository.getQuizQuestions()
                }
            questions.onSuccess { questions ->
                QuestionCache.clear()
                QuestionCache.saveQuestions(questions)
                _eventFlow.emit(WelcomeEvent.NavigateToQuiz)
            }.onFailure { exception ->
                _eventFlow.emit(WelcomeEvent.ShowError("Ошибка: ${exception.message}"))
            }
        }
    }

}