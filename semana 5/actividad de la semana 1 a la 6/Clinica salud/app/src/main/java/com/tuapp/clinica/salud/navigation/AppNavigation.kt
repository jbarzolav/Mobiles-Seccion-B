package com.tuapp.clinica.salud.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tuapp.clinica.salud.ui.drawer.AppDrawer
import com.tuapp.clinica.salud.ui.screens.agendar.AgendarCitaScreen
import com.tuapp.clinica.salud.ui.screens.confirmacion.ConfirmacionScreen
import com.tuapp.clinica.salud.ui.screens.historial.HistorialScreen
import com.tuapp.clinica.salud.ui.screens.home.HomeScreen
import com.tuapp.clinica.salud.ui.screens.miscitas.MisCitasScreen
import com.tuapp.clinica.salud.ui.screens.perfil.PerfilMedicoScreen
import com.tuapp.clinica.salud.ui.screens.perfilusuario.PerfilUsuarioScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route ?: Routes.Inicio.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            AppDrawer(
                rutaActual = rutaActual,
                onNavegar = { ruta ->
                    scope.launch { drawerState.close() }
                    navController.navigate(ruta) {
                        popUpTo(Routes.Inicio.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.Inicio.route
        ) {
            composable(Routes.Inicio.route) {
                HomeScreen(
                    navController = navController,
                    onAbrirDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            }
            composable(
                route = Routes.PerfilMedico.route,
                arguments = listOf(navArgument("medicoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
                PerfilMedicoScreen(
                    navController = navController,
                    medicoId = medicoId
                )
            }
            composable(
                route = Routes.Agendar.route,
                arguments = listOf(
                    navArgument("medicoId") { type = NavType.IntType },
                    navArgument("fecha") { type = NavType.StringType },
                    navArgument("hora") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
                AgendarCitaScreen(
                    navController = navController,
                    medicoId = medicoId
                )
            }
            composable(
                route = Routes.Confirmacion.route,
                arguments = listOf(
                    navArgument("medicoId") { type = NavType.IntType },
                    navArgument("fecha") { type = NavType.StringType },
                    navArgument("hora") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
                val fecha = backStackEntry.arguments?.getString("fecha") ?: ""
                val hora = backStackEntry.arguments?.getString("hora") ?: ""
                ConfirmacionScreen(
                    navController = navController,
                    medicoId = medicoId,
                    fecha = fecha,
                    hora = hora
                )
            }
            composable(Routes.MisCitas.route) {
                MisCitasScreen(navController = navController)
            }
            composable(Routes.Historial.route) {
                HistorialScreen(navController = navController)
            }
            composable(Routes.Perfil.route) {
                PerfilUsuarioScreen(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaceholderScreen(
    titulo: String,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
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
