package com.toadthegod.dailyquiz.domain.model.question

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    val questionText: String,
    val correctAnswer: String,
    val allAnswers: List<String>,
    val category: String,
    val difficulty: String
)