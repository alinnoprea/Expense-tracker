package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val OrganicLightColorScheme = lightColorScheme(
    primary = SageForest,
    onPrimary = Color.White,
    primaryContainer = SageLightContainer,
    onPrimaryContainer = SageOnContainer,
    secondary = SageMuted,
    onSecondary = Color.White,
    tertiary = OchreWarm,
    onTertiary = SlateDark,
    background = WarmCreamBg,
    onBackground = SlateDark,
    surface = SurfaceWhite,
    onSurface = SlateDark,
    surfaceVariant = WarmCreamBg,
    onSurfaceVariant = SlateMutedText,
    outline = EarthyBorder,
    error = TerracottaWarn,
    onError = Color.White
)

private val OrganicDarkColorScheme = darkColorScheme(
    primary = SageForestDark,
    onPrimary = SlateDark,
    primaryContainer = SageDarkContainer,
    onPrimaryContainer = SageForestDark,
    secondary = SageMuted,
    onSecondary = Color.White,
    tertiary = OchreWarm,
    onTertiary = SlateDark,
    background = EarthyBackgroundDark,
    onBackground = WarmCreamBg,
    surface = SurfaceDark,
    onSurface = WarmCreamBg,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = Color(0xFFA5B5A7),
    outline = EarthyBorderDark,
    error = TerracottaWarn,
    onError = Color.White
)

@Composable
fun ExpenseTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false to enforce our distinctive organic earthy aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> OrganicDarkColorScheme
        else -> OrganicLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

