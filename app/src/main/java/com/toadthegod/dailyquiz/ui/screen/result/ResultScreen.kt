package com.toadthegod.dailyquiz.ui.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.toadthegod.DailyQuчiz.ui.component.AccentButton
import com.toadthegod.dailyquiz.R
import com.toadthegod.dailyquiz.ui.ScreenTransitions
import com.toadthegod.dailyquiz.ui.component.RatingBar
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
        else-> stringResource(R.string.worst_result, rating, maxRating)
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
        else-> stringResource(R.string.worst_result_title)
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
    val viewModel: ResultViewModel = koinViewModel<ResultViewModel>()
    val quizRating by viewModel.quizRating.collectAsState()
    val results by viewModel.results.collectAsState()

    val rating = quizRating!!.rating
    val maxRating = quizRating!!.maxRating


    Scaffold { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.result_screen_name),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(vertical = 40.dp)
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                            onClick = { navigator.navigate(QuizStartScreenDestination) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 6.dp, end = 6.dp)
                                .height(56.dp),
                            text = stringResource(R.string.restart),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}