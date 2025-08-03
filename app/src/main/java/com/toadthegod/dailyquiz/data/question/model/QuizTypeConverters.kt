package com.toadthegod.dailyquiz.data.question.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.toadthegod.dailyquiz.domain.model.question.Answer
import com.toadthegod.dailyquiz.domain.model.question.Question


@ProvidedTypeConverter
class QuizTypeConverters(moshi: Moshi) {

    private val questionsListType = Types.newParameterizedType(List::class.java, Question::class.java)
    private val questionsListAdapter = moshi.adapter<List<Question>>(questionsListType)

    private val answersListType = Types.newParameterizedType(List::class.java, Answer::class.java)
    private val answersListAdapter = moshi.adapter<List<Answer>>(answersListType)

    @TypeConverter
    fun fromQuestionsList(questions: List<Question>?): String {
        return questionsListAdapter.toJson(questions)
    }

    @TypeConverter
    fun toQuestionsList(questionsJson: String): List<Question>? {
        return questionsListAdapter.fromJson(questionsJson)
    }

    @TypeConverter
    fun fromAnswersList(answers: List<Answer>?): String {
        return answersListAdapter.toJson(answers)
    }

    @TypeConverter
    fun toAnswersList(answersJson: String): List<Answer>? {
        return answersListAdapter.fromJson(answersJson)
    }
}