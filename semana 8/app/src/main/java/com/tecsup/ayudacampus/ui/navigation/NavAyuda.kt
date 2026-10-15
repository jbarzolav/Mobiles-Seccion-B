package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.soporte.CategoriasScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel
import kotlinx.coroutines.launch

@Composable
fun NavAyuda(repo: AyudaRepository) {
    val navController = rememberNavController()
    val factory = remember(repo) { AyudaViewModelFactory(repo) }
    NavHost(navController = navController, startDestination = Rutas.SPLASH) {
        composable(Rutas.SPLASH) {
            SplashScreen(onContinuar = {
                navController.navigate(Rutas.CATEGORIAS) {
                    popUpTo(Rutas.SPLASH) { inclusive = true }
                    launchSingleTop = true
                }
            })
        }
        composable(Rutas.CATEGORIAS) {
            val vm: CategoriasViewModel = viewModel(factory = factory)
            val snackbar = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            Scaffold(
                topBar = { AppTopBar("Categorías") },
                snackbarHost = { SnackbarHost(snackbar) }
            ) { padding ->
                CategoriasScreen(vm, onEditar = {
                    scope.launch { snackbar.showSnackbar("La edición se implementará en el paso 11") }
                }, modifier = Modifier.padding(padding))
            }
        }
    }
}
