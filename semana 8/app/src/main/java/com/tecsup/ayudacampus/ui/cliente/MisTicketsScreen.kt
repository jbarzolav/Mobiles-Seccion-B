package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel

@Composable
fun MisTicketsScreen(vm: MisTicketsViewModel, onDetalle: (Int) -> Unit, onNuevo: () -> Unit, onPerfil: () -> Unit) {
    val tickets by vm.tickets.collectAsStateWithLifecycle()
    val abiertos by vm.abiertos.collectAsStateWithLifecycle()
    val estado by vm.estado.collectAsStateWithLifecycle()
    val busqueda by vm.busqueda.collectAsStateWithLifecycle()
    Scaffold(
        floatingActionButton = { FloatingActionButton(onClick = onNuevo) { Icon(Icons.Outlined.Add, "Nuevo ticket") } },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = { Icon(Icons.Outlined.List, null) }, label = { Text("Tickets") })
                NavigationBarItem(selected = false, onClick = onNuevo, icon = { Icon(Icons.Outlined.Add, null) }, label = { Text("Nuevo") })
                NavigationBarItem(selected = false, onClick = onPerfil, icon = { Icon(Icons.Outlined.Person, null) }, label = { Text("Perfil") })
            }
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Spacer(Modifier.height(12.dp))
            Text("Mis tickets", style = MaterialTheme.typography.headlineSmall)
            Text("Hola, ${vm.usuario.nombre.substringBefore(' ')} · $abiertos abiertos", style = MaterialTheme.typography.bodyMedium)
            AyudaTextField(busqueda, vm::buscar, "Buscar por título o número…", Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Outlined.Search, null) })
            Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf(null to "Todos", EstadoTicket.ABIERTO to "Abiertos", EstadoTicket.EN_PROCESO to "En proceso", EstadoTicket.RESUELTO to "Resueltos").forEach { (valor, etiqueta) ->
                    FilterChip(selected = estado == valor, onClick = { vm.seleccionarEstado(valor) }, label = { Text(etiqueta) })
                }
            }
            if (tickets.isEmpty()) Text("No tienes tickets aquí", Modifier.padding(vertical = 24.dp))
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 88.dp)) {
                items(tickets, key = { it.ticket.id }) { detalle ->
                    val t = detalle.ticket
                    TicketCard(t.titulo, detalle.categoria.nombre, "#${t.id.toString().padStart(4, '0')}",
                        t.ubicacion, tiempoTranscurrido(t.fecha), EstadoTicket.valueOf(t.estado), Prioridad.valueOf(t.prioridad),
                        onClick = { onDetalle(t.id) }, iconoCategoria = {
                            val icono = when (detalle.categoria.icono) {
                                "pc" -> Icons.Outlined.Computer
                                "code" -> Icons.Outlined.Code
                                "wifi" -> Icons.Outlined.Wifi
                                "key" -> Icons.Outlined.Key
                                "printer" -> Icons.Outlined.Print
                                else -> Icons.Outlined.Label
                            }
                            Icon(icono, null)
                        })
                }
            }
        }
    }
}

private fun tiempoTranscurrido(fecha: Long): String {
    val minutos = ((System.currentTimeMillis() - fecha) / 60_000).coerceAtLeast(0)
    return when {
        minutos < 1 -> "Ahora"
        minutos < 60 -> "Hace $minutos min"
        minutos < 1440 -> "Hace ${minutos / 60} h"
        else -> "Hace ${minutos / 1440} d"
    }
}
