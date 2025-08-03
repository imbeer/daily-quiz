package com.toadthegod.dailyquiz.ui.screen.result

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toadthegod.dailyquiz.R
import com.toadthegod.dailyquiz.domain.model.question.Answer
import com.toadthegod.dailyquiz.domain.model.question.Question
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import com.toadthegod.dailyquiz.ui.component.AccentButton
import com.toadthegod.dailyquiz.ui.component.AnswerState
import com.toadthegod.dailyquiz.ui.component.NormalButton
import com.toadthegod.dailyquiz.ui.component.RatingBar
import com.toadthegod.dailyquiz.ui.component.SelectableAnswerRow
import com.toadthegod.dailyquiz.ui.theme.DailyQuizTheme
import com.toadthegod.dailyquiz.ui.theme.rating
import org.koin.androidx.compose.koinViewModel

@Composable
fun Description(rating: Int, maxRating: Int = 5) {

    val ratingDifference = maxRating - rating

    val text = when (ratingDifference) {
        0 -> stringResource(R.string.victory, rating, maxRating)
        1 -> stringResource(R.string.almost_victory, rating, maxRating)
        2 -> stringResource(R.string.ok_result, rating, maxRating)
        3 -> stringResource(R.string.bad_result, rating, maxRating)
        4 -> stringResource(R.string.almost_worst_result, rating, maxRating)
        else -> stringResource(R.string.worst_result, rating, maxRating)
    }

    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSecondary,
        lineHeight = 22.sp
    )
}

@Composable
fun Title(rating: Int, maxRating: Int = 5) {
    val ratingDifference = maxRating - rating

    val text = when (ratingDifference) {
        0 -> stringResource(R.string.victory_title)
        1 -> stringResource(R.string.almost_victory_title)
        2 -> stringResource(R.string.ok_result_title)
        3 -> stringResource(R.string.bad_result_title)
        4 -> stringResource(R.string.almost_worst_result_title)
        else -> stringResource(R.string.worst_result_title)
    }

    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Destination<RootGraph>(style = ScreenTransitions::class)
@Composable
fun ResultScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: ResultViewModel = koinViewModel()
    val quizRating by viewModel.quizRating.collectAsState()
    val results by viewModel.results.collectAsState()

    if (quizRating == null || results.isEmpty()) {
        return
    }

    ResultScreenContent(
        rating = quizRating!!.rating,
        maxRating = quizRating!!.maxRating,
        results = results,
        onRestartClick = { navigator.navigate(QuizStartScreenDestination) }
    )
}

@Composable
fun ResultScreenContent(
    rating: Int,
    maxRating: Int,
    results: List<Result>,
    onRestartClick: () -> Unit
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 40.dp, bottom = 40.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.result_screen_name),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                )
            }

            item {
                ResultSummaryCard(
                    rating = rating,
                    maxRating = maxRating,
                    onRestartClick = onRestartClick
                )
            }

            itemsIndexed(
                results,
                key = { _, result -> result.question.questionText }) { index, result ->
                QuestionReviewCard(
                    questionIndex = index,
                    totalQuestions = maxRating,
                    result = result
                )
            }

            item {
                TryAgainButton( onClick = onRestartClick )
            }
        }
    }
}


@Composable
fun ResultSummaryCard(
    rating: Int,
    maxRating: Int,
    onRestartClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RatingBar(
                rating = rating,
                maxRating = maxRating,
                starSize = 46.dp,
                spacing = 12.dp,
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "$rating из $maxRating",
                color = MaterialTheme.colorScheme.rating,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(24.dp))
            Title(rating = rating, maxRating = maxRating)
            Spacer(Modifier.height(12.dp))
            Description(rating = rating, maxRating = maxRating)
            Spacer(Modifier.height(52.dp))
            AccentButton(
                onClick = onRestartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 6.dp, end = 6.dp)
                    .height(56.dp),
                text = stringResource(R.string.restart),
            )
        }
    }
}

@Composable
fun QuestionReviewCard(
    questionIndex: Int,
    totalQuestions: Int,
    result: Result
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(
                        R.string.question_progress,
                        questionIndex + 1,
                        totalQuestions
                    ),
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                if (result.answer.isCorrect) {
                    Image(
                        painter = painterResource(id = R.drawable.radio_button_correct),
                        contentDescription = "Correct Answer",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Text(
                text = result.question.questionText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            result.question.allAnswers.forEach { answerText ->
                val state = determineAnswerState(
                    answerText = answerText,
                    correctAnswer = result.question.correctAnswer,
                    selectedAnswer = result.answer.selectedOption
                )
                SelectableAnswerRow(
                    text = answerText,
                    state = state,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun TryAgainButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        NormalButton(
            onClick = onClick,
            modifier = Modifier
                .width(260.dp)
                .padding(start = 6.dp, end = 6.dp)
                .height(50.dp),
            text = stringResource(R.string.restart),
        )
    }
}

private fun determineAnswerState(
    answerText: String,
    correctAnswer: String,
    selectedAnswer: String?
): AnswerState {
    val isCorrect = answerText == correctAnswer
    val isSelected = answerText == selectedAnswer

    return when {
        isCorrect -> AnswerState.CORRECT
        isSelected -> AnswerState.INCORRECT
        else -> AnswerState.UNSELECTED
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenContentPreview() {
    val fakeResults = listOf(
        Result(
            question = Question(
                "Как переводится слово «apple»?",
                "Яблоко",
                listOf("Груша", "Яблоко", "Апельсин", "Ананас"),
                "",
                ""
            ),
            answer = Answer(true, "Яблоко")
        ),
        Result(
            question = Question(
                "Какое слово означает цвет?",
                "Red",
                listOf("Table", "Chair", "Red", "Book"),
                "",
                ""
            ),
            answer = Answer(false, "Table")
        )
    )
    DailyQuizTheme {
        ResultScreenContent(
            rating = 4,
            maxRating = 5,
            results = fakeResults,
            onRestartClick = {}
        )
    }
}