package com.toadthegod.dailyquiz.ui.screen.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import com.toadthegod.dailyquiz.ui.screen.welcome.QuizStartViewModel
import org.koin.androidx.compose.koinViewModel


@Destination<RootGraph>(style = ScreenTransitions::class)
@Composable
fun QuestionScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<QuestionViewModel>()


    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text("biba")
        }
    }
}