package com.toadthegod.dailyquiz.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toadthegod.dailyquiz.data.question.QuizHistoryRepository
import com.toadthegod.dailyquiz.data.question.QuizResult
import com.toadthegod.dailyquiz.ui.screen.welcome.WelcomeEvent
import com.toadthegod.dailyquiz.ui.screen.welcome.WelcomeUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface HistoryEvent {
    data object Deleted : HistoryEvent
}

class HistoryViewModel(private val quizHistoryRepository: QuizHistoryRepository) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<HistoryEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val quizHistory: StateFlow<List<QuizResult>> = quizHistoryRepository.getQuizHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteQuiz(quizResult: QuizResult) {
        viewModelScope.launch {
            quizHistoryRepository.deleteQuizResult(quizResult)
            _eventFlow.emit(HistoryEvent.Deleted)
        }
    }
}