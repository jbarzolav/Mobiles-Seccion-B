package com.tuapp.clinica.salud.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuapp.clinica.salud.ui.screens.home.HomeScreen

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
        composable(Routes.PerfilMedico.route) {
            PlaceholderScreen(titulo = "Perfil del médico")
        }
        composable(Routes.Agendar.route) {
            PlaceholderScreen(titulo = "Agendar cita")
        }
        composable(Routes.Confirmacion.route) {
            PlaceholderScreen(titulo = "Confirmación")
        }
        composable(Routes.MisCitas.route) {
            PlaceholderScreen(titulo = "Mis citas")
        }
        composable(Routes.Historial.route) {
            PlaceholderScreen(titulo = "Historial médico")
        }
        composable(Routes.Perfil.route) {
            PlaceholderScreen(titulo = "Perfil")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaceholderScreen(titulo: String) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(titulo) }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = titulo)
        }
    }
}
