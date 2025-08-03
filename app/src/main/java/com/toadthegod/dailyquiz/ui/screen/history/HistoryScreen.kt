package com.toadthegod.dailyquiz.ui.screen.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.QuizStartScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.toadthegod.DailyQuчiz.ui.component.AccentButton
import com.toadthegod.dailyquiz.R
import com.toadthegod.dailyquiz.data.question.QuizResult
import com.toadthegod.dailyquiz.domain.model.question.Quiz
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import com.toadthegod.dailyquiz.ui.component.RatingBar
import com.toadthegod.dailyquiz.ui.theme.DailyQuizTheme
import com.toadthegod.dailyquiz.util.formatDate
import com.toadthegod.dailyquiz.util.formatTime
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(style = ScreenTransitions::class)
@Composable
fun HistoryScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: HistoryViewModel = koinViewModel()
    val historyState by viewModel.quizHistory.collectAsState()

    HistoryScreenContent(
        historyItems = historyState,
        onDeleteClick = { quizResult ->
            viewModel.deleteQuiz(quizResult)
        },
        onStartQuizClick = { navigator.navigate(QuizStartScreenDestination) },
        onReturnBackClick = { navigator.navigateUp() }
    )
}


@Composable
fun HistoryScreenContent(
    historyItems: List<QuizResult>,
    onDeleteClick: (QuizResult) -> Unit,
    onStartQuizClick: () -> Unit,
    onReturnBackClick: () -> Unit,
) {
    Scaffold { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 60.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = onReturnBackClick,
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.Transparent)
                            .align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Text(
                        text = stringResource(R.string.history),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))
            }

            if (historyItems.isEmpty()) {
                item {
                    EmptyHistoryScreen(onStartQuizClick = onStartQuizClick)
                }
            } else {
                items(historyItems, key = { it.id }) { item ->
                    HistoryItemCard(
                        quizResult = item,
                        onDelete = { onDeleteClick(item) }
                    )
                }
            }
        }
    }
}


@Composable
fun EmptyHistoryScreen(onStartQuizClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxHeight()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.no_quiz_completed),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                AccentButton(
                    onClick = onStartQuizClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 6.dp),
                    text = stringResource(R.string.start_quiz),
                    enabled = true
                )
            }
        }

        Image(
            modifier = Modifier.padding(horizontal = 70.dp, vertical = 64.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo),
//            alignment = Alignment.BottomCenter
        )
    }
}


@Composable
fun HistoryItemCard(
    quizResult: QuizResult,
    onDelete: () -> Unit
) {
    var showDeleteMenu by remember { mutableStateOf(false) }

    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { showDeleteMenu = true }
                    )
                },
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.quiz_number, quizResult.id),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.outline
                    )
                    RatingBar(
                        rating = quizResult.correctAnswersCount(),
                        maxRating = quizResult.totalQuestions(),
                        starSize = 16.dp,
                        spacing = 8.dp,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatDate(quizResult.timestamp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = formatTime(quizResult.timestamp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        if (showDeleteMenu) {
            Popup(
                onDismissRequest = { showDeleteMenu = false },
                alignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .clickable {
                            onDelete()
                            showDeleteMenu = false
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.trash_icon),
                            contentDescription = stringResource(R.string.delete),
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.delete),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Пустой экран")
@Composable
fun HistoryScreenContent_Preview_Empty() {
    DailyQuizTheme {
        HistoryScreenContent(
            historyItems = emptyList(),
            onDeleteClick = {},
            onStartQuizClick = {},
            onReturnBackClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Экран с данными")
@Composable
fun HistoryScreenContent_Preview_WithData() {
    val fakeItems = listOf(
        QuizResult(1, System.currentTimeMillis() - 86400000, Quiz(emptyList(), mutableListOf())),
        QuizResult(2, System.currentTimeMillis() - 172800000, Quiz(emptyList(), mutableListOf())),
        QuizResult(3, System.currentTimeMillis() - 259200000, Quiz(emptyList(), mutableListOf())),
        QuizResult(4, System.currentTimeMillis() - 259200000, Quiz(emptyList(), mutableListOf())),
        QuizResult(5, System.currentTimeMillis() - 259200000, Quiz(emptyList(), mutableListOf())),
        QuizResult(6, System.currentTimeMillis() - 259200000, Quiz(emptyList(), mutableListOf())),
        QuizResult(7, System.currentTimeMillis() - 259200000, Quiz(emptyList(), mutableListOf())),
        QuizResult(8, System.currentTimeMillis() - 259200000, Quiz(emptyList(), mutableListOf())),
        QuizResult(9, System.currentTimeMillis() - 259200000, Quiz(emptyList(), mutableListOf())),
    )
    DailyQuizTheme {
        HistoryScreenContent(
            historyItems = fakeItems,
            onDeleteClick = {},
            onStartQuizClick = {},
            onReturnBackClick = {}
        )
    }
}