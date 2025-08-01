package com.toadthegod.DailyQuiz.data.question

import android.util.Log
import com.toadthegod.DailyQuiz.data.question.network.QuestionApiService
import com.toadthegod.DailyQuiz.domain.model.question.Question
import com.toadthegod.DailyQuiz.data.question.model.toDomain

class QuestionRepository(private val apiService: QuestionApiService) {
    suspend fun getQuizQuestions(
        amount: Int,
        category: Int?,
        difficulty: String?
    ): Result<List<Question>> {
        return try {
            val response = apiService.getQuiz(
                amount = amount,
                category = category,
                difficulty = difficulty
            )

            if (response.responseCode == 0) {
                val domainQuestions = response.results.map { it.toDomain() }
                Log.d("QuizRepository", "Successfully fetched ${domainQuestions.size} questions.")
                Result.success(domainQuestions)
            } else {
                val errorMessage = "API returned an error code: ${response.responseCode}"
                Log.e("QuizRepository", errorMessage)
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("QuizRepository", "Network request failed", e)
            Result.failure(e)
        }
    }
}