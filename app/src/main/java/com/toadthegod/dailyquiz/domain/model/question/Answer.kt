package com.toadthegod.dailyquiz.domain.model.question

data class Answer(
    val isCorrect: Boolean = false,
    val selectedOption: String? = null
)
