package com.tuapp.clinica.tecsupfit.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.clinica.tecsupfit.ui.screens.detalle.DetalleClaseScreen
import com.tuapp.clinica.tecsupfit.ui.screens.home.HomeScreen
import com.tuapp.clinica.tecsupfit.ui.screens.reservas.ReservasScreen
import com.tuapp.clinica.tecsupfit.ui.screens.rutinas.RutinasScreen
import com.tuapp.clinica.tecsupfit.ui.screens.perfil.PerfilScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Inicio.route
    ) {
        composable(Routes.Inicio.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Routes.DetalleClase.route,
            arguments = listOf(navArgument("claseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val claseId = backStackEntry.arguments?.getInt("claseId") ?: 0
            DetalleClaseScreen(
                navController = navController,
                claseId = claseId
            )
        }
        composable(Routes.Reservas.route) {
            ReservasScreen(navController = navController)
        }
        composable(Routes.Rutinas.route) {
            RutinasScreen(navController = navController)
        }
        composable(Routes.Perfil.route) {
            PerfilScreen(navController = navController)
        }
    }
}
