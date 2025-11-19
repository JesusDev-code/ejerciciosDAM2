package com.example.tarea1_tema4.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = TealPrimary,
    onPrimary = TextOnPrimary,
    secondary = TealLight,
    onSecondary = TextPrimary,
    background = TealBackground,
    surface = CardBackground,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundLight,
    outline = BorderLight
)

@Composable
fun MultimediaAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}