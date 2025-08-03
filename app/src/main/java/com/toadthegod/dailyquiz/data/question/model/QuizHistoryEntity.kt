package com.toadthegod.dailyquiz.data.question.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.toadthegod.dailyquiz.domain.model.question.Answer
import com.toadthegod.dailyquiz.domain.model.question.Question

@Entity(tableName = "quiz_history")
data class QuizHistoryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val questions: List<Question>,
    val answers: List<Answer>
)