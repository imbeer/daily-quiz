package com.toadthegod.dailyquiz.data.question.model

import android.text.Html
import com.toadthegod.dailyquiz.domain.model.question.Question

fun QuestionDTO.toDomain(): Question {
    val decodedQuestion = Html.fromHtml(this.question, Html.FROM_HTML_MODE_LEGACY).toString()
    val decodedCorrectAnswer = Html.fromHtml(this.correctAnswer, Html.FROM_HTML_MODE_LEGACY).toString()
    val decodedIncorrectAnswers = this.incorrectAnswers.map { answer ->
        Html.fromHtml(answer, Html.FROM_HTML_MODE_LEGACY).toString()
    }

    val allAnswers = (decodedIncorrectAnswers + decodedCorrectAnswer).shuffled()

    return Question(
        questionText = decodedQuestion,
        correctAnswer = decodedCorrectAnswer,
        allAnswers = allAnswers,
        category = this.category,
        difficulty = this.difficulty
    )
}