package com.tcastro.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    secondary = secondary,
    tertiary = tertiary,
    background = bg,
    surface = surface,
    onSurface = OnSurface,
    onSurfaceVariant = OnSurfaceVariant,
    error = error,
    outline = placeholder,
    primaryContainer = primaryContainer,
    secondaryContainer = secondaryContainer
)


@Composable
fun SwordCatChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}