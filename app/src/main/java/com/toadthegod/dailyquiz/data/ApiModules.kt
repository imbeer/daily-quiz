package com.toadthegod.dailyquiz.data
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.toadthegod.dailyquiz.data.question.QuestionRepository
import com.toadthegod.dailyquiz.data.question.QuizCache
import com.toadthegod.dailyquiz.data.question.network.QuestionApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASEURL = "https://opentdb.com/"

val networkModule = module {
    single {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    single {
        get<Retrofit>().create(QuestionApiService::class.java)
    }
}

val repositoryModule = module {
    single {
        QuestionRepository(get())
    }
    single {
        QuizCache()
    }
}