package com.toadthegod.dailyquiz.data.question

import com.toadthegod.dailyquiz.data.question.database.QuizHistoryDao
import com.toadthegod.dailyquiz.data.question.model.QuizHistoryEntry
import com.toadthegod.dailyquiz.data.question.model.QuizTypeConverters
import com.toadthegod.dailyquiz.domain.model.question.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class QuizResult(
    val id: Int,
    val timestamp: Long,
    val quiz: Quiz
)

class QuizHistoryRepository(
    private val quizHistoryDao: QuizHistoryDao,
    private val converters: QuizTypeConverters
) {

    fun getQuizHistory(): Flow<List<QuizResult>> {
        return quizHistoryDao.getAllQuizzes().map { entries ->
            entries.mapNotNull { entry ->
                val questions = converters.toQuestionsList(entry.questionsJson)
                val answers = converters.toAnswersList(entry.answersJson)

                if (questions != null && answers != null) {
                    QuizResult(
                        id = entry.id,
                        timestamp = entry.timestamp,
                        quiz = Quiz(
                            questions = questions,
                            answers = answers.toMutableList()
                        )
                    )
                } else {
                    null
                }
            }
        }
    }

    suspend fun saveCompletedQuiz(quiz: Quiz) {
        val entry = QuizHistoryEntry(
            timestamp = System.currentTimeMillis(),
            questionsJson = converters.fromQuestionsList(quiz.questions),
            answersJson = converters.fromAnswersList(quiz.answers)
        )
        quizHistoryDao.insertQuiz(entry)
    }

    suspend fun deleteQuizResult(quizResult: QuizResult) {
        val entryToDelete = QuizHistoryEntry(
            id = quizResult.id,
            timestamp = quizResult.timestamp,
            questionsJson = "",
            answersJson = ""
        )
        quizHistoryDao.deleteQuiz(entryToDelete)
    }
}