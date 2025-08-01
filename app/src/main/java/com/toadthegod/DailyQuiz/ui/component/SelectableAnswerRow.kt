package com.toadthegod.DailyQuiz.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toadthegod.DailyQuiz.R
import com.toadthegod.DailyQuiz.ui.theme.success

enum class AnswerState {
    UNSELECTED,
    SELECTED,
    CORRECT,
    INCORRECT
}

@Composable
@Preview
fun SelectableAnswerRow(
    modifier: Modifier = Modifier,
    text: String = "text",
    state: AnswerState = AnswerState.SELECTED,
    onClick: () -> Unit = {}
) {
    data class AnswerStyle(
        val borderColor: Color,
        val backgroundColor: Color,
        val textColor: Color,
        val iconId: Int,
    )

    val style = when (state) {
        AnswerState.UNSELECTED -> AnswerStyle(
            borderColor = MaterialTheme.colorScheme.outline,
            backgroundColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.secondary,
            iconId = R.drawable.radio_button_unselected
        )
        AnswerState.SELECTED -> AnswerStyle(
            borderColor = MaterialTheme.colorScheme.outline,
            backgroundColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.primary,
            iconId = R.drawable.radio_button_selected
        )
        AnswerState.CORRECT -> AnswerStyle(
            borderColor = MaterialTheme.colorScheme.success,
            backgroundColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.success,
            iconId = R.drawable.radio_button_correct
        )
        AnswerState.INCORRECT -> AnswerStyle(
            borderColor = MaterialTheme.colorScheme.error,
            backgroundColor = MaterialTheme.colorScheme.surface,
            textColor = MaterialTheme.colorScheme.error,
            iconId = R.drawable.radio_button_incorrect
        )
    }

    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16),
        color = style.backgroundColor,
        border = BorderStroke(1.dp, style.borderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(style.iconId),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = style.textColor
            )
        }
    }
}