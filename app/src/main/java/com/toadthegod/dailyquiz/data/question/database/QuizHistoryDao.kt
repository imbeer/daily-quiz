package com.toadthegod.dailyquiz.data.question.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toadthegod.dailyquiz.data.question.model.QuizHistoryEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuiz(quizEntry: QuizHistoryEntry): Long

    @Delete
    suspend fun delete(quizEntry: QuizHistoryEntry)

    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC")
    fun getAllQuizzes(): Flow<List<QuizHistoryEntry>>
}