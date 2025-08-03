package com.toadthegod.dailyquiz.ui.screen.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toadthegod.dailyquiz.data.question.QuizCache
import com.toadthegod.dailyquiz.data.question.QuizHistoryRepository
import com.toadthegod.dailyquiz.domain.model.question.Answer
import com.toadthegod.dailyquiz.domain.model.question.Question
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class QuizState(
    val number: Int = 0,
    val size: Int = 5
)

class QuestionViewModel(
    private val quizCache: QuizCache,
    private val quizHistoryRepository: QuizHistoryRepository
) : ViewModel() {
    private val _question = MutableStateFlow<Question?>(null)
    val question: StateFlow<Question?> = _question
    private val _questionState = MutableStateFlow<QuizState?>(QuizState(1, 5))
    val questionState: StateFlow<QuizState?> = _questionState

    init {
        loadCurrentQuestion()
    }

    private fun loadCurrentQuestion() {
        _question.value = quizCache.getCurrentQuestion()
        _questionState.value = QuizState(quizCache.getCurrentIndex() + 1, quizCache.getQuestions().size)
    }

    fun nextQuestion() {
        val hasNext = quizCache.next()
        if (hasNext) {
            loadCurrentQuestion()
        }
    }

    fun saveAnswer(selectedOption: String) {
        quizCache.getCurrentQuiz().answers[quizCache.getCurrentIndex()] = Answer(
            isCorrect = selectedOption == _question.value?.correctAnswer,
            selectedOption = selectedOption
        )
    }

    fun reset() {
        quizCache.clear()
        _question.value = null
        _questionState.value = null
    }

    fun finish() {
        viewModelScope.launch {
            quizHistoryRepository.saveCompletedQuiz(quizCache.getCurrentQuiz())
        }
    }


}