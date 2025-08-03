package com.toadthegod.dailyquiz.data.question

import com.toadthegod.dailyquiz.domain.model.question.Answer
import com.toadthegod.dailyquiz.domain.model.question.Question
import com.toadthegod.dailyquiz.domain.model.question.Quiz

class QuizCache {
    private var currentQuiz: Quiz = Quiz(emptyList(), mutableListOf())
    private var currentQuestion: Int = 0

    fun saveQuestions(newQuestions: List<Question>) {
        currentQuiz = Quiz(
            questions = newQuestions,
            answers = MutableList(newQuestions.size) { Answer() }
        )
    }

    fun getQuestions(): List<Question> {
        return currentQuiz.questions
    }

    fun getCurrentQuestion(): Question {
        return currentQuiz.questions[currentQuestion]
    }

    fun getCurrentIndex(): Int {
        return currentQuestion
    }

    fun getCurrentQuiz(): Quiz {
        return currentQuiz
    }

    fun next(): Boolean {
        ++currentQuestion
        return currentQuestion < currentQuiz.questions.size
    }

    fun clear() {
        currentQuiz = Quiz(emptyList(), mutableListOf())
        currentQuestion = 0
    }
}