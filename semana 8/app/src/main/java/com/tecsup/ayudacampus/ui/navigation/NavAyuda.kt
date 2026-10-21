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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.cliente.LoginScreen
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import com.tecsup.ayudacampus.viewmodel.SesionViewModel
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.ui.cliente.MisTicketsScreen
import com.tecsup.ayudacampus.ui.cliente.NuevoTicketScreen
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel

@Composable
fun NavAyuda(repo: AyudaRepository, preferencias: PreferenciasStore) {
    val navController = rememberNavController()
    val factory = remember(repo, preferencias) { AyudaViewModelFactory(repo, preferencias) }
    val sesion: SesionViewModel = viewModel(factory = factory)
    val usuario by sesion.usuarioActual.collectAsStateWithLifecycle()
    val lista by sesion.lista.collectAsStateWithLifecycle()
    val oscuro by sesion.modoOscuro.collectAsStateWithLifecycle()
    val rol = if (usuario?.rol == Rol.ADMIN.name) Rol.ADMIN else Rol.CLIENTE
    AyudaTheme(rol = rol, darkTheme = oscuro) {
    NavHost(navController = navController, startDestination = Rutas.SPLASH) {
        composable(Rutas.SPLASH) {
            var tiempoCumplido by rememberSaveable { mutableStateOf(false) }
            LaunchedEffect(lista, tiempoCumplido) {
                if (lista && tiempoCumplido) navController.navigate(
                    if (usuario == null) Rutas.LOGIN else if (rol == Rol.ADMIN) Rutas.PANEL else Rutas.MIS_TICKETS
                ) {
                    popUpTo(Rutas.SPLASH) { inclusive = true }
                    launchSingleTop = true
                }
            }
            SplashScreen(onContinuar = { tiempoCumplido = true })
        }
        composable(Rutas.LOGIN) {
            LaunchedEffect(usuario) {
                if (usuario != null) navController.navigate(if (rol == Rol.ADMIN) Rutas.PANEL else Rutas.MIS_TICKETS) {
                    popUpTo(Rutas.LOGIN) { inclusive = true }
                    launchSingleTop = true
                }
            }
            LoginScreen(sesion)
        }
        composable(Rutas.MIS_TICKETS) {
            usuario?.let { actual ->
                val ticketsFactory = remember(actual) { AyudaViewModelFactory(repo, preferencias, actual) }
                val vm: MisTicketsViewModel = viewModel(key = "tickets-${actual.id}", factory = ticketsFactory)
                MisTicketsScreen(vm,
                    onDetalle = { navController.navigate(Rutas.detalleTicket(it)) },
                    onNuevo = { navController.navigate(Rutas.NUEVO_TICKET) },
                    onPerfil = { navController.navigate(Rutas.PERFIL) })
            }
        }
        composable(Rutas.NUEVO_TICKET) {
            usuario?.let { actual ->
                val nuevoFactory = remember(actual) { AyudaViewModelFactory(repo, preferencias, actual) }
                val vm: NuevoTicketViewModel = viewModel(factory = nuevoFactory)
                NuevoTicketScreen(vm,
                    onVolver = { navController.popBackStack() },
                    onEnviado = { navController.popBackStack(Rutas.MIS_TICKETS, false) },
                    onPerfil = { navController.navigate(Rutas.PERFIL) })
            }
        }
        composable(Rutas.DETALLE_TICKET) { entrada ->
            DestinoProvisional("Ticket #${entrada.arguments?.getString("id")}", usuario?.nombre.orEmpty(), "Detalle pendiente del paso 9") {
                TextButton(onClick = { navController.popBackStack() }) { Text("Volver") }
            }
        }
        composable(Rutas.PERFIL) {
            DestinoProvisional("Perfil", usuario?.nombre.orEmpty(), "Perfil pendiente del paso 12") {
                TextButton(onClick = { navController.popBackStack() }) { Text("Volver") }
            }
        }
        composable(Rutas.PANEL) {
            DestinoProvisional("Panel de soporte", usuario?.nombre.orEmpty(), "El panel se implementará en el paso 10") {
                TextButton(onClick = { navController.navigate(Rutas.CATEGORIAS) }) { Text("Categorías") }
            }
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
}

@Composable
private fun DestinoProvisional(titulo: String, nombre: String, mensaje: String, contenido: @Composable () -> Unit = {}) {
    Scaffold(topBar = { AppTopBar(titulo) }) { padding ->
        Column(Modifier.padding(padding).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Hola, $nombre", style = MaterialTheme.typography.titleLarge)
            Text("Sesión iniciada correctamente")
            Text(mensaje)
            contenido()
        }
    }
}
