package com.toadthegod.dailyquiz.ui.screen.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.QuizStartScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ResultScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toadthegod.dailyquiz.ui.component.AccentButton
import com.toadthegod.dailyquiz.R
import com.toadthegod.dailyquiz.domain.model.question.Question
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import com.toadthegod.dailyquiz.ui.component.AnswerState
import com.toadthegod.dailyquiz.ui.component.SelectableAnswerRow
import com.toadthegod.dailyquiz.ui.theme.DailyQuizTheme
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(style = ScreenTransitions::class)
@Composable
fun QuestionScreen(navigator: DestinationsNavigator) {
    val viewModel: QuestionViewModel = koinViewModel()
    val currentQuestion by viewModel.question.collectAsState()
    val quizState by viewModel.questionState.collectAsState()

    var selectedOption by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(currentQuestion) {
        selectedOption = null
    }

    QuestionScreenContent(
        question = currentQuestion,
        quizState = quizState,
        selectedOption = selectedOption,
        onAnswerSelected = { option ->
            selectedOption = if (selectedOption == option) null else option
        },
        onNextClick = {
            viewModel.saveAnswer(selectedOption!!)
            viewModel.nextQuestion()
        },
        onFinishClick = {
            viewModel.saveAnswer(selectedOption!!)
            viewModel.finish()
            navigator.navigate(ResultScreenDestination) {
                popUpTo(QuizStartScreenDestination) { inclusive = true }
            }
        },
        onBackClick = {
            navigator.popBackStack(QuizStartScreenDestination, inclusive = false)
        }
    )
}

@Composable
fun QuestionScreenContent(
    question: Question?,
    quizState: QuizState?,
    selectedOption: String?,
    onAnswerSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    onFinishClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            QuestionTopBar(
                isFinalQuestion = quizState?.number == quizState?.size,
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (question == null || quizState == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                QuestionCard(
                    question = question,
                    quizState = quizState,
                    selectedOption = selectedOption,
                    onAnswerSelected = onAnswerSelected,
                    onNextClick = onNextClick,
                    onFinishClick = onFinishClick
                )
            }
        }
    }
}

@Composable
private fun QuestionTopBar(isFinalQuestion: Boolean, onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        if (!isFinalQuestion) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.dailyquiz_logo),
            modifier = Modifier
                .width(180.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun QuestionCard(
    question: Question,
    quizState: QuizState,
    selectedOption: String?,
    onAnswerSelected: (String) -> Unit,
    onNextClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.question_progress, quizState.number, quizState.size),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = question.questionText,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                question.allAnswers.forEach { option ->
                    SelectableAnswerRow(
                        text = option,
                        state = if (selectedOption == option) AnswerState.SELECTED else AnswerState.UNSELECTED,
                        onClick = { onAnswerSelected(option) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            val isFinalQuestion = quizState.number == quizState.size
            AccentButton(
                text = if (!isFinalQuestion) stringResource(R.string.next_question) else stringResource(R.string.finish_quiz),
                enabled = selectedOption != null,
                onClick = if (!isFinalQuestion) onNextClick else onFinishClick,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(R.string.no_come_back),
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun QuestionScreenContentPreview() {
    val previewQuestion = Question(
        questionText = "What is the capital of France?",
        allAnswers = listOf("Berlin", "Madrid", "Paris", "Rome"),
        correctAnswer = "Paris",
        category = "Geography",
        difficulty = "Easy"
    )
    val previewQuizState = QuizState(number = 3, size = 10)

    DailyQuizTheme {
        QuestionScreenContent(
            question = previewQuestion,
            quizState = previewQuizState,
            selectedOption = "Paris",
            onAnswerSelected = {},
            onNextClick = {},
            onFinishClick = {},
            onBackClick = {}
        )
    }
}