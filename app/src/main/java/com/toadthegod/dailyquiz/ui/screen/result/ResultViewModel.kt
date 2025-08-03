package com.toadthegod.dailyquiz.ui.screen.result

import androidx.lifecycle.ViewModel
import com.toadthegod.dailyquiz.data.question.QuizCache
import com.toadthegod.dailyquiz.domain.model.question.Answer
import com.toadthegod.dailyquiz.domain.model.question.Question
import com.toadthegod.dailyquiz.domain.model.question.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class QuizRating(
    val rating: Int = 0,
    val maxRating: Int = 5,
)

data class Result(
    val question: Question,
    val answer: Answer,
)


class ResultViewModel(
    private val quizCache: QuizCache
) : ViewModel() {

    private val _quizRating = MutableStateFlow<QuizRating?>(null)
    val quizRating: StateFlow<QuizRating?> = _quizRating

    private val _results = MutableStateFlow<List<Result>>(emptyList())
    val results: StateFlow<List<Result>> = _results

    init {
        loadLastQuiz()
    }

    private fun loadLastQuiz() {
        val quiz = quizCache.getCurrentQuiz()
        val maxRating = quiz.questions.size
        val rating = quiz.answers.count { answer -> answer.isCorrect }
        _quizRating.value = QuizRating(maxRating = maxRating, rating = rating)
        val results: List<Result> = quiz.questions.zip(quiz.answers) { question, answer ->
            Result(question = question, answer = answer)
        }
        _results.value = results
    }
}