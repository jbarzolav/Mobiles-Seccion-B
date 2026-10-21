package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.components.*
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NuevoTicketScreen(
    vm: NuevoTicketViewModel,
    recibirTickets: Boolean = true,
    onVolver: () -> Unit,
    onEnviado: () -> Unit,
    onPerfil: () -> Unit
) {
    val datos by vm.formulario.collectAsStateWithLifecycle()
    val categorias by vm.categorias.collectAsStateWithLifecycle()
    LaunchedEffect(datos.enviado) { if (datos.enviado) onEnviado() }
    Scaffold(
        topBar = { AppTopBar("Nuevo ticket", onVolver = onVolver) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(false, onVolver, icon = { Icon(Icons.AutoMirrored.Outlined.List, null) }, label = { Text("Tickets") })
                NavigationBarItem(true, {}, icon = { Icon(Icons.Outlined.Add, null) }, label = { Text("Nuevo") })
                NavigationBarItem(false, onPerfil, icon = { Icon(Icons.Outlined.Person, null) }, label = { Text("Perfil") })
            }
        }
    ) { padding ->
        Column(
            Modifier.padding(padding).imePadding().fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!recibirTickets) {
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Outlined.Info, contentDescription = null, tint = MaterialTheme.colorScheme.error)
                        Text(
                            "Mesa de ayuda cerrada. En este momento no se están recibiendo nuevos tickets.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }

            Text("Categoría", style = MaterialTheme.typography.titleSmall)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                categorias.forEach { categoria ->
                    FilterChip(
                        selected = datos.categoriaId == categoria.id,
                        onClick = { vm.categoria(categoria.id) },
                        enabled = !datos.enviando && recibirTickets,
                        label = { Text(categoria.nombre) },
                        leadingIcon = {
                            Icon(
                                when (categoria.icono) {
                                    "pc" -> Icons.Outlined.Computer
                                    "code" -> Icons.Outlined.Code
                                    "wifi" -> Icons.Outlined.Wifi
                                    "key" -> Icons.Outlined.Key
                                    "printer" -> Icons.Outlined.Print
                                    else -> Icons.AutoMirrored.Outlined.Label
                                }, null, Modifier.size(18.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                            selectedLeadingIconColor = Color.White
                        )
                    )
                }
            }
            datos.errorCategoria?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            AyudaTextField(
                datos.titulo, vm::titulo, "Título", Modifier.fillMaxWidth(),
                enabled = !datos.enviando && recibirTickets,
                isError = datos.errorTitulo != null, supportingText = datos.errorTitulo
            )
            AyudaTextField(
                datos.ubicacion, vm::ubicacion, "Ubicación", Modifier.fillMaxWidth(),
                enabled = !datos.enviando && recibirTickets,
                leadingIcon = { Icon(Icons.Outlined.LocationOn, null) }
            )
            Text("Prioridad", style = MaterialTheme.typography.titleSmall)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(Prioridad.BAJA, Prioridad.MEDIA, Prioridad.ALTA).forEach { prioridad ->
                    FilterChip(
                        selected = datos.prioridad == prioridad,
                        onClick = { vm.prioridad(prioridad) },
                        enabled = !datos.enviando && recibirTickets,
                        label = { Text(prioridad.etiqueta) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = prioridad.color(),
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
            AyudaTextField(
                datos.descripcion, vm::descripcion, "Descripción", Modifier.fillMaxWidth(),
                enabled = !datos.enviando && recibirTickets,
                singleLine = false, minLines = 4, isError = datos.errorDescripcion != null,
                supportingText = datos.errorDescripcion
            )
            datos.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            AyudaButton(
                "Enviar ticket", vm::enviar, Modifier.fillMaxWidth(),
                cargando = datos.enviando, enabled = recibirTickets && !datos.enviado
            )
        }
    }
}
