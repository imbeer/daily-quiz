package com.toadthegod.dailyquiz.data.question

import com.toadthegod.dailyquiz.domain.model.question.Question

object QuestionCache {
    private var questions: List<Question> = emptyList()
    private var currentQuestion: Int = 0

    fun saveQuestions(newQuestions: List<Question>) {
        questions = newQuestions
    }

    fun getQuestions(): List<Question> {
        return questions
    }

    fun getCurrent(): Question {
        return questions[currentQuestion]
    }

    fun next(): Boolean {
        ++currentQuestion
        return currentQuestion < questions.size - 1
    }

    fun clear() {
        questions = emptyList()
        currentQuestion = 0
    }
}