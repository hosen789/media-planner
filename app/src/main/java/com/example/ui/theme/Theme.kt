package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldPrimaryContainer,
    onPrimary = EmeraldOnPrimaryContainer,
    primaryContainer = EmeraldPrimary,
    onPrimaryContainer = EmeraldPrimaryContainer,
    secondary = NavySecondaryContainer,
    onSecondary = NavyOnSecondaryContainer,
    secondaryContainer = NavySecondary,
    onSecondaryContainer = NavySecondaryContainer,
    tertiary = AmberTertiaryContainer,
    onTertiary = AmberOnTertiaryContainer,
    tertiaryContainer = AmberTertiary,
    onTertiaryContainer = AmberTertiaryContainer,
    background = DarkBackground,
    onBackground = ColorTokens.DarkOnBackground,
    surface = DarkSurface,
    onSurface = ColorTokens.DarkOnSurface,
    surfaceVariant = DarkSurfaceContainer,
    onSurfaceVariant = ColorTokens.DarkOnSurfaceVariant,
    outline = DarkBorder,
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = EmeraldOnPrimary,
    primaryContainer = EmeraldPrimaryContainer,
    onPrimaryContainer = EmeraldOnPrimaryContainer,
    secondary = NavySecondary,
    onSecondary = ColorTokens.LightOnSecondary,
    secondaryContainer = NavySecondaryContainer,
    onSecondaryContainer = NavyOnSecondaryContainer,
    tertiary = AmberTertiary,
    onTertiary = ColorTokens.LightOnTertiary,
    tertiaryContainer = AmberTertiaryContainer,
    onTertiaryContainer = AmberOnTertiaryContainer,
    background = SurfaceLight,
    onBackground = ColorTokens.LightOnBackground,
    surface = ColorTokens.LightSurface,
    onSurface = ColorTokens.LightOnSurface,
    surfaceVariant = SurfaceContainerLight,
    onSurfaceVariant = ColorTokens.LightOnSurfaceVariant,
    outline = BorderLight,
)

private object ColorTokens {
    val LightSurface = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    val LightOnSurface = androidx.compose.ui.graphics.Color(0xFF18222C)
    val LightOnBackground = androidx.compose.ui.graphics.Color(0xFF18222C)
    val LightOnSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF5E6B78)
    val LightOnSecondary = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    val LightOnTertiary = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
    val DarkOnSurface = androidx.compose.ui.graphics.Color(0xFFE8EFF4)
    val DarkOnBackground = androidx.compose.ui.graphics.Color(0xFFE8EFF4)
    val DarkOnSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFB9C6D0)
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
