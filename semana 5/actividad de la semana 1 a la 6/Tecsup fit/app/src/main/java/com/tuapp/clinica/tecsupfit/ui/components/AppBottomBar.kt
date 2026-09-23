package com.tuapp.clinica.tecsupfit.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tuapp.clinica.tecsupfit.navigation.Routes

private data class PestanaBottomBar(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
)

private val pestanas = listOf(
    PestanaBottomBar(Routes.Inicio.route, "Inicio", Icons.Default.Home),
    PestanaBottomBar(Routes.Reservas.route, "Reservas", Icons.Default.DateRange),
    PestanaBottomBar(Routes.Rutinas.route, "Rutinas", Icons.AutoMirrored.Filled.List),
    PestanaBottomBar(Routes.Perfil.route, "Perfil", Icons.Default.Person)
)

@Composable
fun AppBottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    NavigationBar {
        pestanas.forEach { pestana ->
            NavigationBarItem(
                selected = rutaActual == pestana.ruta,
                onClick = {
                    navController.navigate(pestana.ruta) {
                        popUpTo(Routes.Inicio.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = pestana.icono,
                        contentDescription = pestana.titulo
                    )
                },
                label = { Text(pestana.titulo) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}
