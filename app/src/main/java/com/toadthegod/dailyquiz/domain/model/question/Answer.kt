package com.toadthegod.dailyquiz.domain.model.question

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Answer(
    val isCorrect: Boolean = false,
    val selectedOption: String? = null
)
