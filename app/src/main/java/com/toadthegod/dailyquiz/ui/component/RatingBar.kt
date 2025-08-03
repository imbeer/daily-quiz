package com.toadthegod.dailyquiz.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.toadthegod.dailyquiz.R
import com.toadthegod.dailyquiz.ui.theme.DailyQuizTheme
import com.toadthegod.dailyquiz.ui.theme.rating

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    maxRating: Int = 5,
    starSize: Dp = 24.dp,
    spacing: Dp = 4.dp,
    filledColor: Color = MaterialTheme.colorScheme.rating,
    emptyColor: Color = MaterialTheme.colorScheme.tertiary
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        repeat(maxRating) { index ->
            val isFilled = index < rating
            val starPainter = painterResource(id = R.drawable.star_icon)

            Icon(
                painter = starPainter,
                contentDescription = null,
                tint = if (isFilled) filledColor else emptyColor,
                modifier = Modifier.size(starSize)
            )
        }
    }
}


@Preview(showBackground = true, widthDp = 300)
@Composable
fun CustomRatingBarPreview() {
    DailyQuizTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RatingBar(rating = 4)

            RatingBar(rating = 2)
        }
    }
}