package com.toadthegod.DailyQuчiz.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color,
    contentColor: Color,
    enabled: Boolean = true
) {
    val backgroundColor = if (enabled) {
        color
    } else {
        MaterialTheme.colorScheme.tertiary
    }

    val textColor = if (enabled) {
        contentColor
    } else {
        MaterialTheme.colorScheme.onTertiary
    }

    val onClickFiltered = if (enabled) {
        onClick
    } else {
        {}
    }

    Surface(
        onClick = onClickFiltered,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16),
        color = backgroundColor,
    ) {
        Box(
            modifier = Modifier.padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text.uppercase(),
                color = textColor,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun NormalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        text = text,
        onClick = onClick,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary,
        enabled = enabled
    )
}

@Composable
fun AccentButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier,
        text = text,
        onClick = onClick,
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        enabled = enabled
    )
}