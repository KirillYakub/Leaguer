package com.kiras.leaguer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val darkColor = darkColorScheme(
    primary = DarkBlue,
    onPrimary = White,
    background = DarkGray,
    onBackground = White
)

private val lightColor = lightColorScheme(
    primary = LightBlue,
    onPrimary = White,
    background = White,
    onBackground = DarkGray
)

@Composable
fun LeaguerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColor else lightColor
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}