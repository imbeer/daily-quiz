package com.toadthegod.dailyquiz.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = lightColorScheme(
    primary = Lilac,
    onPrimary = White,
    secondary = SemiWhite,
    onSecondary = Color.Black,
    tertiary = Grey,
    onTertiary = White,
    surface = White,
    onSurface = LightLilac,
    background = Lilac,
    outline = DarkLilac,
    error = Red,
)

val ColorScheme.success: Color
    @Composable
    get() = Green

val ColorScheme.rating: Color
    @Composable
    get() = Yellow

@Composable
fun DailyQuizTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}