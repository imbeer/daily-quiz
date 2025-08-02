package com.toadthegod.dailyquiz.ui

import com.toadthegod.dailyquiz.ui.screen.quiz.QuestionViewModel
import com.toadthegod.dailyquiz.ui.screen.welcome.QuizStartViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { QuizStartViewModel(questionRepository = get()) }
    viewModel { QuestionViewModel() }
}