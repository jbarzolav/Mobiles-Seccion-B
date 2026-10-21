package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.components.*
import com.tecsup.ayudacampus.viewmodel.AtenderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AtenderTicketScreen(
    vm: AtenderViewModel,
    numeroTicket: Int,
    onVolver: () -> Unit,
    onPanel: () -> Unit,
    onTickets: () -> Unit,
    onCategorias: () -> Unit,
    onAjustes: () -> Unit
) {
    val detalle by vm.ticket.collectAsStateWithLifecycle()
    val estadoSeleccionado by vm.estadoSeleccionado.collectAsStateWithLifecycle()
    val prioridadSeleccionada by vm.prioridadSeleccionada.collectAsStateWithLifecycle()
    val guardado by vm.guardado.collectAsStateWithLifecycle()
    var respuestaTexto by remember { mutableStateOf("") }

    LaunchedEffect(guardado) {
        if (guardado) {
            onVolver()
        }
    }

    Scaffold(
        topBar = { AppTopBar("Atender ticket #${numeroTicket.toString().padStart(4, '0')}", onVolver = onVolver) },
        bottomBar = {
            BarraSoporte("tickets", onPanel = onPanel, onTickets = onTickets,
                onCategorias = onCategorias, onAjustes = onAjustes)
        }
    ) { padding ->
        if (detalle == null) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }
        val t = detalle!!
        val estadoActual = EstadoTicket.valueOf(t.ticket.estado)
        val esCerrado = estadoActual == EstadoTicket.CERRADO

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
        ) {
            // Card Top: Datos del ticket y del cliente
            item {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(t.ticket.titulo, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "${t.cliente.nombre} · ${t.ticket.ubicacion} · ${t.categoria.nombre}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(t.ticket.descripcion, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // Selector de Estado
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Estado", style = MaterialTheme.typography.titleSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val estados = listOf(
                            EstadoTicket.ABIERTO,
                            EstadoTicket.EN_PROCESO,
                            EstadoTicket.RESUELTO,
                            EstadoTicket.CERRADO
                        )
                        estados.forEach { est ->
                            val seleccionado = (estadoSeleccionado ?: estadoActual) == est
                            val esOpcionCerrado = est == EstadoTicket.CERRADO
                            val chipHabilitado = !esCerrado && !esOpcionCerrado

                            SelectorChip(
                                texto = est.etiqueta,
                                seleccionado = seleccionado,
                                enabled = chipHabilitado,
                                colorSeleccionado = MaterialTheme.colorScheme.primary,
                                onClick = { vm.cambiarEstado(est) }
                            )
                        }
                    }
                }
            }

            // Selector de Prioridad
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Prioridad", style = MaterialTheme.typography.titleSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val prioridades = Prioridad.entries.toTypedArray()
                        val actualP = Prioridad.valueOf(t.ticket.prioridad)
                        prioridades.forEach { prio ->
                            val seleccionado = (prioridadSeleccionada ?: actualP) == prio
                            SelectorChip(
                                texto = prio.etiqueta,
                                seleccionado = seleccionado,
                                enabled = !esCerrado,
                                colorSeleccionado = prio.color(),
                                onClick = { vm.cambiarPrioridad(prio) }
                            )
                        }
                    }
                }
            }

            // Conversación
            item { Text("Conversación", style = MaterialTheme.typography.titleSmall) }

            if (t.comentarios.isEmpty()) {
                item {
                    Text(
                        "Todavía no hay comentarios",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(t.comentarios, key = { it.id }) { c ->
                    BurbujaComentario(
                        mensaje = c.texto,
                        autor = if (c.autorId == vm.usuario.id) "Tú" else c.autorNombre,
                        hora = horaDe(c.fecha),
                        propio = c.autorId == vm.usuario.id
                    )
                }
            }

            // Campo para responder con botón enviar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AyudaTextField(
                        value = respuestaTexto,
                        onValueChange = { respuestaTexto = it },
                        label = "",
                        placeholder = "Responder al cliente...",
                        modifier = Modifier.weight(1f),
                        enabled = !esCerrado
                    )
                    IconButton(
                        onClick = {
                            if (respuestaTexto.isNotBlank()) {
                                vm.responder(respuestaTexto)
                                respuestaTexto = ""
                            }
                        },
                        enabled = !esCerrado && respuestaTexto.isNotBlank(),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                        )
                    ) {
                        Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = "Enviar respuesta")
                    }
                }
            }

            // Botón 'Guardar cambios'
            item {
                AyudaButton(
                    texto = "Guardar cambios",
                    onClick = { vm.guardar() },
                    enabled = !esCerrado,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun SelectorChip(
    texto: String,
    seleccionado: Boolean,
    enabled: Boolean = true,
    colorSeleccionado: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        color = when {
            !enabled -> MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.5f)
            seleccionado -> colorSeleccionado
            else -> MaterialTheme.colorScheme.surface
        },
        contentColor = when {
            !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            seleccionado -> Color.White
            else -> MaterialTheme.colorScheme.onSurface
        },
        border = if (!seleccionado && enabled) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

private fun horaDe(fecha: Long) =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(fecha))
