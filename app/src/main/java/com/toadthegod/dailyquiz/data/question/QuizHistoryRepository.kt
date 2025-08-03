package com.toadthegod.dailyquiz.data.question

import com.toadthegod.dailyquiz.data.question.database.QuizHistoryDao
import com.toadthegod.dailyquiz.data.question.model.QuizHistoryEntry
import com.toadthegod.dailyquiz.domain.model.question.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class QuizResult(
    val id: Int,
    val timestamp: Long,
    val quiz: Quiz
) {
    fun correctAnswersCount(): Int {
        return quiz.answers.count { answer -> answer.isCorrect }
    }

    fun totalQuestions(): Int {
        return quiz.questions.size
    }
}

class QuizHistoryRepository(
    private val quizHistoryDao: QuizHistoryDao,
) {

    fun getQuizHistory(): Flow<List<QuizResult>> {
        return quizHistoryDao.getAllQuizzes().map { entries ->
            entries.map { entry ->
                QuizResult(
                    id = entry.id,
                    timestamp = entry.timestamp,
                    quiz = Quiz(
                        questions = entry.questions,
                        answers = entry.answers.toMutableList()
                    )
                )
            }
        }
    }

    suspend fun saveCompletedQuiz(quiz: Quiz) {
        val entry = QuizHistoryEntry(
            timestamp = System.currentTimeMillis(),
            questions = quiz.questions,
            answers = quiz.answers
        )
        quizHistoryDao.insertQuiz(entry)
    }

    suspend fun deleteQuizResult(quizResult: QuizResult) {
        val entryToDelete = QuizHistoryEntry(
            id = quizResult.id,
            timestamp = quizResult.timestamp,
            questions = emptyList(),
            answers = emptyList()
        )
        quizHistoryDao.delete(entryToDelete)
    }
}