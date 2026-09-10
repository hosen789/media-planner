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
  tertiary = AmberTertiaryContainer,
  onTertiary = AmberOnTertiaryContainer,
  background = DarkBackground,
  surface = DarkSurface,
  surfaceVariant = DarkSurfaceContainer,
)

private val LightColorScheme = lightColorScheme(
  primary = EmeraldPrimary,
  onPrimary = EmeraldOnPrimary,
  primaryContainer = EmeraldPrimaryContainer,
  onPrimaryContainer = EmeraldOnPrimaryContainer,
  secondary = NavySecondary,
  secondaryContainer = NavySecondaryContainer,
  onSecondaryContainer = NavyOnSecondaryContainer,
  tertiary = AmberTertiary,
  tertiaryContainer = AmberTertiaryContainer,
  onTertiaryContainer = AmberOnTertiaryContainer,
  background = SurfaceLight,
  surface = SurfaceLight,
  surfaceVariant = SurfaceContainerLight,
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Keep the product palette stable across devices so the Media Planner brand
  // does not change with the phone wallpaper/system dynamic colors.
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
