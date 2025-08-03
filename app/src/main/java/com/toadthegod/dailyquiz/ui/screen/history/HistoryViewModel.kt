package com.toadthegod.dailyquiz.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toadthegod.dailyquiz.data.question.QuizHistoryRepository
import com.toadthegod.dailyquiz.data.question.QuizResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(private val quizHistoryRepository: QuizHistoryRepository) : ViewModel() {

    val quizHistory: StateFlow<List<QuizResult>> = quizHistoryRepository.getQuizHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteQuiz(quizResult: QuizResult) {
        viewModelScope.launch {
            quizHistoryRepository.deleteQuizResult(quizResult)
        }
    }
}