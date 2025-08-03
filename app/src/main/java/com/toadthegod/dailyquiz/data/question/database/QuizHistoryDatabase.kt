package com.toadthegod.dailyquiz.data.question.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.toadthegod.dailyquiz.data.question.model.QuizHistoryEntry
import com.toadthegod.dailyquiz.data.question.model.QuizTypeConverters

@Database(
    entities = [QuizHistoryEntry::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(QuizTypeConverters::class)
abstract class QuizHistoryDatabase : RoomDatabase() {

    abstract fun quizHistoryDao(): QuizHistoryDao

    companion object {
        fun getDatabase(
            context: Context,
            quizTypeConverters: QuizTypeConverters
        ): QuizHistoryDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                QuizHistoryDatabase::class.java,
                "quiz_history_database"
            )
                .addTypeConverter(quizTypeConverters)
                .build()
        }
    }
}