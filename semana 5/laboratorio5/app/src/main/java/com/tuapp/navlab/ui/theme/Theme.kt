package com.tuapp.navlab.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Esquema de colores para Modo Oscuro con garantía de accesibilidad (Contraste AA)
private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimaryLight,
    onPrimary = Color.White,
    primaryContainer = PurplePrimaryDark,
    onPrimaryContainer = Color.White,
    secondary = PurpleSecondaryLight,
    onSecondary = Color.Black,
    tertiary = VioletTertiary,
    background = DarkBackground,
    onBackground = Color(0xFFF3EBF9),
    surface = DarkSurface,
    onSurface = Color(0xFFF3EBF9),
    surfaceVariant = DarkSurfaceContainer,
    onSurfaceVariant = Color(0xFFD0C4DC)
)

// Esquema de colores para Modo Claro con paleta morada institucional
private val LightColorScheme = lightColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,
    primaryContainer = LightSurfaceContainer,
    onPrimaryContainer = PurplePrimaryDark,
    secondary = PurpleSecondary,
    onSecondary = Color.White,
    tertiary = VioletTertiary,
    background = LightBackground,
    onBackground = Color(0xFF1D1B20),
    surface = LightSurface,
    onSurface = Color(0xFF1D1B20),
    surfaceVariant = LightSurfaceContainer,
    onSurfaceVariant = Color(0xFF49454F)
)

// Helper para obtener un degradado vertical consistente según el tema actual (Claro u Oscuro)
@Composable
fun getHeaderGradient(isDark: Boolean = isSystemInDarkTheme()): Brush {
    return if (isDark) {
        Brush.verticalGradient(
            colors = listOf(GradientStartDark, GradientEndDark)
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(GradientStartLight, GradientEndLight)
        )
    }
}

@Composable
fun NavLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Desactivamos el color dinámico para mantener la identidad visual morada uniforme
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
