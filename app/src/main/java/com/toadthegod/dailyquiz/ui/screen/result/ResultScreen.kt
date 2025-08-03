package com.toadthegod.dailyquiz.ui.screen.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import org.koin.androidx.compose.koinViewModel


@Destination<RootGraph>(style = ScreenTransitions::class)
@Composable
fun ResultScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: ResultViewModel = koinViewModel<ResultViewModel>()
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

        }
    }
}