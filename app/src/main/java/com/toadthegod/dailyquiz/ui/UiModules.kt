package com.toadthegod.dailyquiz.ui

import com.toadthegod.dailyquiz.ui.screen.quiz.QuestionViewModel
import com.toadthegod.dailyquiz.ui.screen.result.ResultViewModel
import com.toadthegod.dailyquiz.ui.screen.welcome.QuizStartViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { QuizStartViewModel(questionRepository = get(), quizCache = get()) }
    viewModel { QuestionViewModel(quizCache = get()) }
    viewModel { ResultViewModel(quizCache = get()) }
}