package com.tuapp.navlab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.navlab.screens.DetailScreen
import com.tuapp.navlab.screens.HomeScreen
import com.tuapp.navlab.screens.ListScreen
import com.tuapp.navlab.screens.ProfileScreen

// rememberNavController() crea y mantiene el controlador
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // NavHost es el contenedor del grafo de navegación
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Inicio
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        // Ruta simple
        composable(Screen.List.route) {
            ListScreen(navController)
        }

        // Ruta simple
        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        // Ruta con argumento
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("id") ?: 0
            DetailScreen(navController, itemId)
        }
    }
}

// Desarrollado por Jose Barzola
