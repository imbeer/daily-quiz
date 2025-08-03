package com.toadthegod.dailyquiz.ui.screen.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.QuizStartScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ResultScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toadthegod.DailyQuчiz.ui.component.AccentButton
import com.toadthegod.dailyquiz.R
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import com.toadthegod.dailyquiz.ui.component.AnswerState
import com.toadthegod.dailyquiz.ui.component.SelectableAnswerRow
import org.koin.androidx.compose.koinViewModel


@Destination<RootGraph>(style = ScreenTransitions::class)
@Composable
fun QuestionScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = koinViewModel<QuestionViewModel>()
    val currentQuestion by viewModel.question.collectAsState()
    val currentState by viewModel.questionState.collectAsState()
    var selectedOption by remember { mutableStateOf<String?>(null) }
    val options = currentQuestion?.allAnswers
    val isFinalQuestion = currentState!!.number == currentState!!.size


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {

                if (!isFinalQuestion) {
                    IconButton(
                        onClick = { navigator.navigate(QuizStartScreenDestination) },
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Transparent)
                            .align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = stringResource(R.string.dailyquiz_logo),
                    modifier = Modifier
                        .height(36.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)) {
                    Text(
                        text = String.format(
                            stringResource(R.string.question_number),
                            currentState?.number,
                            currentState?.size
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    currentQuestion?.questionText?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    options?.forEach { option ->
                        SelectableAnswerRow(
                            text = option,
                            state = if (selectedOption == option) AnswerState.SELECTED
                            else AnswerState.UNSELECTED,
                            onClick = {
                                selectedOption = if (selectedOption != option) option else null
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                    AccentButton(
                        text = if (!isFinalQuestion) stringResource(R.string.next_question)
                        else stringResource(R.string.finish_quiz),

                        enabled = selectedOption != null,
                        onClick = if (!isFinalQuestion) ({
                            viewModel.saveAnswer(selectedOption!!)
                            viewModel.nextQuestion()
                        }) else ({
                            viewModel.finish()
                            navigator.navigate(ResultScreenDestination)
                        }),
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }
            }
            Text(
                text = stringResource(R.string.no_come_back),
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}