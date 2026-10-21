package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.cliente.DetalleTicketScreen
import com.tecsup.ayudacampus.ui.cliente.LoginScreen
import com.tecsup.ayudacampus.ui.cliente.MisTicketsScreen
import com.tecsup.ayudacampus.ui.cliente.NuevoTicketScreen
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.soporte.AtenderTicketScreen
import com.tecsup.ayudacampus.ui.soporte.BandejaScreen
import com.tecsup.ayudacampus.ui.soporte.BarraSoporte
import com.tecsup.ayudacampus.ui.soporte.CategoriasScreen
import com.tecsup.ayudacampus.ui.soporte.FormularioCategoriaScreen
import com.tecsup.ayudacampus.ui.soporte.PanelScreen
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import com.tecsup.ayudacampus.viewmodel.AtenderViewModel
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel
import com.tecsup.ayudacampus.viewmodel.PanelViewModel
import com.tecsup.ayudacampus.viewmodel.SesionViewModel
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel

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
                usuario?.let { actual ->
                    val id = entrada.arguments?.getString("id")?.toIntOrNull() ?: return@let
                    val detalleFactory = remember(actual, id) { AyudaViewModelFactory(repo, preferencias, actual, id) }
                    val vm: TicketDetalleViewModel = viewModel(key = "detalle-$id", factory = detalleFactory)
                    DetalleTicketScreen(vm, id,
                        onVolver = { navController.popBackStack() },
                        onNuevo = { navController.navigate(Rutas.NUEVO_TICKET) },
                        onPerfil = { navController.navigate(Rutas.PERFIL) })
                }
            }
            composable(Rutas.PERFIL) {
                DestinoProvisional("Perfil", usuario?.nombre.orEmpty(), "Perfil pendiente del paso 12") {
                    TextButton(onClick = { navController.popBackStack() }) { Text("Volver") }
                }
            }
            composable(Rutas.PANEL) {
                val panelVm: PanelViewModel = viewModel(factory = factory)
                PanelScreen(panelVm,
                    onVerAltas = { navController.navigate(Rutas.BANDEJA) },
                    onTickets = { navController.navigate(Rutas.BANDEJA) },
                    onCategorias = { navController.navigate(Rutas.CATEGORIAS) },
                    onAjustes = {})
            }
            composable(Rutas.BANDEJA) {
                val bandejaVm: BandejaViewModel = viewModel(factory = factory)
                BandejaScreen(bandejaVm,
                    onVolver = { navController.popBackStack() },
                    onAtender = { navController.navigate(Rutas.atenderTicket(it)) },
                    onPanel = { navController.popBackStack(Rutas.PANEL, false) },
                    onCategorias = { navController.navigate(Rutas.CATEGORIAS) },
                    onAjustes = {})
            }
            composable(Rutas.ATENDER_TICKET) { entrada ->
                usuario?.let { actual ->
                    val id = entrada.arguments?.getString("id")?.toIntOrNull() ?: return@let
                    val atenderFactory = remember(actual, id) { AyudaViewModelFactory(repo, preferencias, actual, id) }
                    val vm: AtenderViewModel = viewModel(key = "atender-$id", factory = atenderFactory)
                    AtenderTicketScreen(
                        vm = vm,
                        numeroTicket = id,
                        onVolver = { navController.popBackStack() },
                        onPanel = { navController.popBackStack(Rutas.PANEL, false) },
                        onTickets = { navController.navigate(Rutas.BANDEJA) },
                        onCategorias = { navController.navigate(Rutas.CATEGORIAS) },
                        onAjustes = {}
                    )
                }
            }
            composable(Rutas.CATEGORIAS) {
                val vm: CategoriasViewModel = viewModel(factory = factory)
                val snackbar = remember { SnackbarHostState() }
                val mensaje by vm.mensaje.collectAsStateWithLifecycle()

                LaunchedEffect(mensaje) {
                    mensaje?.let {
                        snackbar.showSnackbar(it)
                        vm.limpiarMensaje()
                    }
                }

                Scaffold(
                    topBar = { AppTopBar("Categorías") },
                    bottomBar = {
                        BarraSoporte("categorias",
                            onPanel = { navController.popBackStack(Rutas.PANEL, false) },
                            onTickets = { navController.navigate(Rutas.BANDEJA) },
                            onCategorias = {},
                            onAjustes = {})
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { navController.navigate(Rutas.formularioCategoria()) }) {
                            Icon(Icons.Outlined.Add, contentDescription = "Nueva categoría")
                        }
                    },
                    snackbarHost = { SnackbarHost(snackbar) }
                ) { padding ->
                    CategoriasScreen(vm, onEditar = { id ->
                        navController.navigate(Rutas.formularioCategoria(id))
                    }, modifier = Modifier.padding(padding))
                }
            }
            composable(
                route = Rutas.FORMULARIO_CATEGORIA,
                arguments = listOf(navArgument("id") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
            ) { entrada ->
                val id = entrada.arguments?.getString("id")?.toIntOrNull()
                val vm: CategoriasViewModel = viewModel(factory = factory)
                FormularioCategoriaScreen(
                    vm = vm,
                    categoriaId = id,
                    onVolver = { navController.popBackStack() }
                )
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
