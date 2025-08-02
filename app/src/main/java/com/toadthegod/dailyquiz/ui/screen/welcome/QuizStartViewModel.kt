package com.toadthegod.dailyquiz.ui.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toadthegod.dailyquiz.data.question.QuestionCache
import com.toadthegod.dailyquiz.data.question.QuestionRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

            try {
                val questions = questionRepository.getQuizQuestions()

                if (questions.isFailure) throw RuntimeException("Ошибка загрузки")
                QuestionCache.clear()
                questions.getOrNull()?.let { QuestionCache.saveQuestions(it) }
                _eventFlow.emit(WelcomeEvent.NavigateToQuiz)
            } catch (e: Exception) {
                _eventFlow.emit(WelcomeEvent.ShowError("Ошибка загрузки. Попробуйте снова."))
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

}