package com.toadthegod.dailyquiz.domain.model.question

data class Question(
    val questionText: String,
    val correctAnswer: String,
    val allAnswers: List<String>,
    val category: String,
    val difficulty: String
)