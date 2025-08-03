package com.toadthegod.dailyquiz.domain.model.question

data class Quiz(
    val questions: List<Question>,
    val answers: MutableList<Answer>
)
