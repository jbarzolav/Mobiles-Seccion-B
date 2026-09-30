package com.tecsup.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PantallaPrincipalStore()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipalStore() {
    val categorias = listOf("Todos", "Más vendidos", "Accesorios", "Cómputo")
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    var opcionSeleccionada by remember { mutableStateOf("Inicio") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val productosFiltrados = if (categoriaSeleccionada == "Todos") {
        listaProductosDemo
    } else {
        listaProductosDemo.filter { it.categoria == categoriaSeleccionada }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú Principal",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    selected = opcionSeleccionada == "Inicio",
                    onClick = {
                        opcionSeleccionada = "Inicio"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Favoritos") },
                    selected = opcionSeleccionada == "Favoritos",
                    onClick = {
                        opcionSeleccionada = "Favoritos"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Perfil") },
                    selected = opcionSeleccionada == "Perfil",
                    onClick = {
                        opcionSeleccionada = "Perfil"
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("TECSUP Store", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú principal",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4A148C))
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (opcionSeleccionada) {
                    "Inicio" -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            LazyRow(
                                modifier = Modifier.padding(vertical = 8.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(categorias) { cat ->
                                    FilterChip(
                                        selected = cat == categoriaSeleccionada,
                                        onClick = { categoriaSeleccionada = cat },
                                        label = { Text(cat) }
                                    )
                                }
                            }

                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(productosFiltrados, key = { it.id }) { producto ->
                                    TarjetaProducto(producto = producto)
                                }
                            }
                        }
                    }
                    "Favoritos" -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sección de Favoritos",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    "Perfil" -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sección de Mi Perfil",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}