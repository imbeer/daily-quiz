package com.toadthegod.dailyquiz.data.question.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuestionDTO(
    @field:Json(name = "category")
    val category: String,

    @field:Json(name = "type")
    val type: String,

    @field:Json(name = "difficulty")
    val difficulty: String,

    @field:Json(name = "question")
    val question: String,

    @field:Json(name = "correct_answer")
    val correctAnswer: String,

    @field:Json(name = "incorrect_answers")
    val incorrectAnswers: List<String>
)

@JsonClass(generateAdapter = true)
data class QuizDTO(
    @field:Json(name = "response_code")
    val responseCode: Int,

    @field:Json(name = "results")
    val results: List<QuestionDTO>
)