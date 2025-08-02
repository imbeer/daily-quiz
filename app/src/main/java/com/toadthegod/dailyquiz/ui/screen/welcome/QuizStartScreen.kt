package com.toadthegod.dailyquiz.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.QuestionScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toadthegod.dailyquiz.R
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import com.toadthegod.DailyQuчiz.ui.component.AccentButton
import org.koin.androidx.compose.koinViewModel


@Composable
fun HistoryButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.history_button),
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                imageVector = Icons.Outlined.History,
                contentDescription = stringResource(R.string.history_button),
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun WelcomeCard(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.welcome_title),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            AccentButton(
                onClick = onClick,
                modifier = Modifier.padding(horizontal = 6.dp),
                text = stringResource(R.string.start_quiz),
                enabled = true,
            )
        }
    }
}


@Composable
fun LoadingState() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo)
        )
        Spacer(modifier = Modifier.height(48.dp))
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 4.dp,
            trackColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun WelcomeState(
    onStartClick: () -> Unit,
    onHistoryClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(208.dp)
        ) {
            HistoryButton(
                modifier = Modifier
                    .align(Alignment.Center),
                onClick = onHistoryClick
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                modifier = Modifier.padding(20.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.logo)
            )
            Spacer(modifier = Modifier.height(42.dp))
            WelcomeCard { onStartClick() }
        }
    }
}


@Destination<RootGraph>(
    start = true,
    style = ScreenTransitions::class
)
@Composable
fun QuizStartScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<QuizStartViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is WelcomeEvent.NavigateToQuiz -> {
                    navigator.navigate(QuestionScreenDestination)
                }

                is WelcomeEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                LoadingState()
            } else {
                WelcomeState(
                    onStartClick = { viewModel.load() },
                    onHistoryClick = { }
                )
            }
        }
    }

}
