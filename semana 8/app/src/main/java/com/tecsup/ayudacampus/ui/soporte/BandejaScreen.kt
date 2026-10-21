package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.TicketCard
import com.tecsup.ayudacampus.viewmodel.BandejaViewModel

@Composable
fun BandejaScreen(
    vm: BandejaViewModel,
    onVolver: () -> Unit,
    onAtender: (Int) -> Unit,
    onPanel: () -> Unit,
    onCategorias: () -> Unit,
    onAjustes: () -> Unit
) {
    val tickets by vm.tickets.collectAsStateWithLifecycle()
    val seleccionado by vm.estadoSeleccionado.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { AppTopBar("Tickets", onVolver = onVolver) },
        bottomBar = {
            BarraSoporteBandeja("tickets", onPanel = onPanel, onTickets = {},
                onCategorias = onCategorias, onAjustes = onAjustes)
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            FilaFiltros(seleccionado, onFiltrar = { vm.filtrarPor(it) })
            LazyColumn(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 20.dp)
            ) {
                items(tickets, key = { it.ticket.id }) { t ->
                    TicketCard(
                        titulo = t.ticket.titulo,
                        categoria = t.cliente.nombre,
                        numero = "#${t.ticket.id.toString().padStart(4, '0')}",
                        ubicacion = "",
                        tiempo = tiempoTranscurrido(t.ticket.fecha),
                        estado = EstadoTicket.valueOf(t.ticket.estado),
                        prioridad = Prioridad.valueOf(t.ticket.prioridad),
                        fecha = t.ticket.fecha,
                        onClick = { onAtender(t.ticket.id) },
                        iconoCategoria = {
                            Icon(iconoDe(t.categoria.icono), null,
                                tint = MaterialTheme.colorScheme.primary)
                        }
                    )
                }
                if (tickets.isEmpty()) {
                    item {
                        Text("No hay tickets con ese estado",
                            Modifier.padding(top = 24.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun FilaFiltros(seleccionado: EstadoTicket?, onFiltrar: (EstadoTicket?) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FiltroChip("Todos", seleccionado == null) { onFiltrar(null) }
        FiltroChip("Abiertos", seleccionado == EstadoTicket.ABIERTO) { onFiltrar(EstadoTicket.ABIERTO) }
        FiltroChip("En proceso", seleccionado == EstadoTicket.EN_PROCESO) { onFiltrar(EstadoTicket.EN_PROCESO) }
        FiltroChip("Resueltos", seleccionado == EstadoTicket.RESUELTO) { onFiltrar(EstadoTicket.RESUELTO) }
    }
}

@Composable
private fun FiltroChip(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
    FilterChip(
        selected = seleccionado,
        onClick = onClick,
        label = { Text(texto) },
        modifier = Modifier.semantics { contentDescription = "Filtro $texto" }
    )
}

/** Barra de navegación del rol Soporte TI; duplicada a propósito para no crear archivos fuera del paso. */
@Composable
private fun BarraSoporteBandeja(
    actual: String,
    onPanel: () -> Unit,
    onTickets: () -> Unit,
    onCategorias: () -> Unit,
    onAjustes: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(actual == "panel", onPanel,
            icon = { Icon(Icons.Outlined.BarChart, null) }, label = { Text("Panel") })
        NavigationBarItem(actual == "tickets", onTickets,
            icon = { Icon(Icons.AutoMirrored.Outlined.List, null) }, label = { Text("Tickets") })
        NavigationBarItem(actual == "categorias", onCategorias,
            icon = { Icon(Icons.AutoMirrored.Outlined.Label, null) }, label = { Text("Categorías") })
        NavigationBarItem(actual == "ajustes", onAjustes,
            icon = { Icon(Icons.Outlined.Settings, null) }, label = { Text("Ajustes") })
    }
}

private fun iconoDe(icono: String): ImageVector = when (icono) {
    "pc" -> Icons.Outlined.Computer
    "code" -> Icons.Outlined.Code
    "wifi" -> Icons.Outlined.Wifi
    "key" -> Icons.Outlined.Key
    "printer" -> Icons.Outlined.Print
    else -> Icons.AutoMirrored.Outlined.Label
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
