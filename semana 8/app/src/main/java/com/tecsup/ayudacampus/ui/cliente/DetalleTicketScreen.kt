package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.automirrored.outlined.Label
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
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto
import com.tecsup.ayudacampus.viewmodel.TicketDetalleViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetalleTicketScreen(
    vm: TicketDetalleViewModel,
    numeroTicket: Int,
    onVolver: () -> Unit,
    onNuevo: () -> Unit,
    onPerfil: () -> Unit
) {
    val detalle by vm.ticket.collectAsStateWithLifecycle()
    var comentario by remember { mutableStateOf("") }
    var comentarioFinal by remember { mutableStateOf("") }
    var calificacion by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { AppTopBar("Ticket #${numeroTicket.toString().padStart(4, '0')}", onVolver = onVolver) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(false, onVolver, icon = { Icon(Icons.AutoMirrored.Outlined.List, null) }, label = { Text("Tickets") })
                NavigationBarItem(false, onNuevo, icon = { Icon(Icons.Outlined.Add, null) }, label = { Text("Nuevo") })
                NavigationBarItem(false, onPerfil, icon = { Icon(Icons.Outlined.Person, null) }, label = { Text("Perfil") })
            }
        }
    ) { padding ->
        if (detalle == null) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }
        val t = detalle!!
        val estado = EstadoTicket.valueOf(t.ticket.estado)
        val cerrado = estado == EstadoTicket.CERRADO
        val resuelto = estado == EstadoTicket.RESUELTO

        LazyColumn(
            Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
        ) {
            item {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(t.ticket.titulo, style = MaterialTheme.typography.titleMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            EstadoChip(estado)
                            PrioridadChip(Prioridad.valueOf(t.ticket.prioridad))
                            Text(tiempoTranscurrido(t.ticket.fecha), Modifier.weight(1f),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = androidx.compose.ui.text.style.TextAlign.End)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(iconoDe(t.categoria.icono), null, Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary)
                            Text("${t.categoria.nombre} · ${t.ticket.ubicacion}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
            item { StepperTicket(estado, Modifier.fillMaxWidth()) }

            if (resuelto) item {
                Surface(color = AvisoResueltoFondo, shape = MaterialTheme.shapes.small) {
                    Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Outlined.CheckCircle, null, tint = EstadoResuelto)
                        Text("Soporte marcó este ticket como resuelto. ¿El problema se solucionó?",
                            style = MaterialTheme.typography.bodySmall, color = AvisoResueltoTexto)
                    }
                }
            }

            item { Text("Conversación", style = MaterialTheme.typography.titleSmall) }
            if (t.comentarios.isEmpty()) {
                item { Text("Todavía no hay comentarios", style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant) }
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

            if (resuelto) item {
                Surface(shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Califica la atención", style = MaterialTheme.typography.titleSmall)
                        Estrellas(calificacion, onCalificacionChange = { calificacion = it })
                    }
                }
            }

            if (resuelto) item {
                AyudaTextField(comentarioFinal, { comentarioFinal = it }, "Comentario final (opcional)",
                    Modifier.fillMaxWidth(), singleLine = false, minLines = 2)
            }

            if (cerrado) item {
                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.small) {
                    Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Outlined.Star, null, tint = MaterialTheme.colorScheme.primary)
                        Text("Calificación recibida: ${t.ticket.calificacion ?: 0} de 5 estrellas",
                            style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            if (resuelto) item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    AyudaButton("Confirmar y cerrar", { vm.cerrar(calificacion, comentarioFinal) },
                        Modifier.fillMaxWidth(), enabled = calificacion in 1..5)
                    OutlinedButton({ vm.reabrir() }, Modifier.fillMaxWidth(),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                        Text("Reabrir ticket")
                    }
                }
            }

            if (!cerrado) item {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AyudaTextField(comentario, { comentario = it }, "Escribe un comentario…",
                        Modifier.weight(1f))
                    IconButton(
                        onClick = { vm.comentar(comentario); comentario = "" },
                        enabled = comentario.isNotBlank(),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary)
                    ) { Icon(Icons.AutoMirrored.Outlined.Send, "Enviar comentario") }
                }
            }
        }
    }
}

private val AvisoResueltoFondo = Color(0xFFDCFCE7)
private val AvisoResueltoTexto = Color(0xFF166534)

private fun iconoDe(icono: String) = when (icono) {
    "pc" -> Icons.Outlined.Computer
    "code" -> Icons.Outlined.Code
    "wifi" -> Icons.Outlined.Wifi
    "key" -> Icons.Outlined.Key
    "printer" -> Icons.Outlined.Print
    else -> Icons.AutoMirrored.Outlined.Label
}

private fun horaDe(fecha: Long) =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(fecha))

private fun tiempoTranscurrido(fecha: Long): String {
    val minutos = ((System.currentTimeMillis() - fecha) / 60_000).coerceAtLeast(0)
    return when {
        minutos < 1 -> "Ahora"
        minutos < 60 -> "Hace $minutos min"
        minutos < 1440 -> "Hace ${minutos / 60} h"
        else -> "Hace ${minutos / 1440} d"
    }
}
