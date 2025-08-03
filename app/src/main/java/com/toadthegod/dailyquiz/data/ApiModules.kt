package com.toadthegod.dailyquiz.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.toadthegod.dailyquiz.data.question.QuestionRepository
import com.toadthegod.dailyquiz.data.question.QuizCache
import com.toadthegod.dailyquiz.data.question.QuizHistoryRepository
import com.toadthegod.dailyquiz.data.question.database.QuizHistoryDatabase
import com.toadthegod.dailyquiz.data.question.model.QuizTypeConverters
import com.toadthegod.dailyquiz.data.question.network.QuestionApiService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

const val BASEURL = "https://opentdb.com/"

val networkModule = module {

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create(QuestionApiService::class.java) }
}

val repositoryModule = module {
    single { QuestionRepository(get()) }
    single { QuizCache() }
}

val dbModule = module {
    single { QuizTypeConverters(get()) }
    single {
        QuizHistoryDatabase.getDatabase(
            context = androidContext(),
            quizTypeConverters = get()
        )
    }
    single { get<QuizHistoryDatabase>().quizHistoryDao() }
    single { QuizHistoryRepository(get()) }
}