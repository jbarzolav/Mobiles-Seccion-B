package com.tecsup.ayudacampus.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Rol

private fun esquemaClaro(rol: Rol) = lightColorScheme(
    primary = if (rol == Rol.CLIENTE) ClientePrimario else SoportePrimario,
    onPrimary = Color.White,
    primaryContainer = if (rol == Rol.CLIENTE) ClienteContenedor else SoporteContenedor,
    onPrimaryContainer = if (rol == Rol.CLIENTE) ClienteContenedorOscuro else SoporteContenedorOscuro,
    secondary = if (rol == Rol.CLIENTE) SoportePrimario else ClientePrimario,
    onSecondary = Color.White,
    secondaryContainer = if (rol == Rol.CLIENTE) SoporteContenedor else ClienteContenedor,
    onSecondaryContainer = TextoPrincipal,
    background = if (rol == Rol.CLIENTE) ClienteFondo else SoporteFondo,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal,
    surfaceVariant = SuperficieSuave,
    onSurfaceVariant = TextoSecundario,
    surfaceContainer = SuperficieSuave,
    surfaceContainerLow = Color.White,
    surfaceContainerHigh = Borde,
    outline = TextoSecundario,
    outlineVariant = Borde,
    error = PrioridadAlta,
    onError = Color.White
)

private fun esquemaOscuro(rol: Rol) = darkColorScheme(
    primary = if (rol == Rol.CLIENTE) ClientePrimarioOscuro else SoportePrimarioOscuro,
    onPrimary = FondoOscuro,
    primaryContainer = if (rol == Rol.CLIENTE) ClienteContenedorOscuro else SoporteContenedorOscuro,
    onPrimaryContainer = if (rol == Rol.CLIENTE) ClienteContenedor else SoporteContenedor,
    secondary = if (rol == Rol.CLIENTE) SoportePrimarioOscuro else ClientePrimarioOscuro,
    onSecondary = FondoOscuro,
    secondaryContainer = if (rol == Rol.CLIENTE) SoporteContenedorOscuro else ClienteContenedorOscuro,
    onSecondaryContainer = TextoOscuro,
    background = FondoOscuro,
    onBackground = TextoOscuro,
    surface = SuperficieOscura,
    onSurface = TextoOscuro,
    surfaceVariant = BordeOscuro,
    onSurfaceVariant = TextoSecundarioOscuro,
    surfaceContainer = SuperficieOscura,
    surfaceContainerLow = FondoOscuro,
    surfaceContainerHigh = BordeOscuro,
    outline = TextoSecundarioOscuro,
    outlineVariant = BordeOscuro,
    error = Color(0xFFFCA5A5),
    onError = FondoOscuro
)

/** Paleta fija del laboratorio; el color dinámico no sustituye el color del perfil. */
@Composable
fun AyudaTheme(
    rol: Rol = Rol.CLIENTE,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) esquemaOscuro(rol) else esquemaClaro(rol),
        typography = Typography,
        shapes = Shapes(
            extraSmall = RoundedCornerShape(4.dp),
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(12.dp),
            large = RoundedCornerShape(16.dp),
            extraLarge = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}

@Composable
fun AyudaTecsupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    AyudaTheme(rol = Rol.CLIENTE, darkTheme = darkTheme, content = content)
}
