package com.toadthegod.dailyquiz.data.question.network
import com.toadthegod.dailyquiz.data.question.model.QuizDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface QuestionApiService {
    @GET("api.php")
    suspend fun getQuiz(
        @Query("amount") amount: Int,
        @Query("category") category: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String = "multiple"
    ): QuizDTO
}