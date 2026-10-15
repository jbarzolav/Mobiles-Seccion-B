package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.theme.AyudaTheme

/** Recibe datos de presentación, sin depender de una entidad Room. */
@Composable
fun TicketCard(
    titulo: String,
    categoria: String,
    numero: String,
    ubicacion: String,
    tiempo: String,
    estado: EstadoTicket,
    prioridad: Prioridad,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    iconoCategoria: (@Composable () -> Unit)? = null
) {
    val contenido: @Composable () -> Unit = {
        Row(Modifier.height(IntrinsicSize.Min)) {
            Box(Modifier.width(5.dp).fillMaxHeight().background(prioridad.color()))
            Column(Modifier.weight(1f).padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Surface(shape = MaterialTheme.shapes.small, color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary) {
                        Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                            if (iconoCategoria != null) iconoCategoria()
                            else Text(categoria.take(1).uppercase(), style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(titulo, style = MaterialTheme.typography.titleSmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                        Text(listOf(numero, categoria, ubicacion).filter { it.isNotBlank() }.joinToString(" · "),
                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    EstadoChip(estado)
                    PrioridadChip(prioridad)
                    Text(tiempo, modifier = Modifier.weight(1f), style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.End)
                }
            }
        }
    }
    if (onClick == null) {
        Surface(modifier = modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant), content = contenido)
    } else {
        Surface(onClick = onClick, modifier = modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant), content = contenido)
    }
}

@Preview(name = "Componentes · Estudiante", showBackground = true, widthDp = 390, heightDp = 840)
@Composable
private fun ComponentesClientePreview() { AyudaTheme(Rol.CLIENTE, darkTheme = false) { MuestraComponentes() } }

@Preview(name = "Componentes · Soporte TI", showBackground = true, widthDp = 390, heightDp = 840)
@Composable
private fun ComponentesSoportePreview() { AyudaTheme(Rol.ADMIN, darkTheme = false) { MuestraComponentes() } }

@Preview(name = "Componentes · Oscuro", showBackground = true, widthDp = 390, heightDp = 840)
@Composable
private fun ComponentesOscuroPreview() { AyudaTheme(Rol.CLIENTE, darkTheme = true) { MuestraComponentes() } }

/** Catálogo de diseño para Preview; no es una pantalla ni un flujo de la aplicación. */
@Composable
private fun MuestraComponentes() {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            AppTopBar("AyudaCampus", onVolver = {})
            TicketCard("Sin Wi-Fi en el laboratorio 3", "Red / Wi-Fi", "#0012", "Lab 3", "Hace 2 h",
                EstadoTicket.EN_PROCESO, Prioridad.ALTA)
            TicketCard("Teclado no responde", "Hardware", "#0011", "Aula 204", "Ayer",
                EstadoTicket.ABIERTO, Prioridad.MEDIA)
            TicketCard("No puedo imprimir en biblioteca", "Impresión", "#0009", "Biblioteca", "Hace 3 d",
                EstadoTicket.RESUELTO, Prioridad.BAJA)
            StepperTicket(EstadoTicket.EN_PROCESO)
            BurbujaComentario("Revisaremos el access point del pabellón B hoy.", "Soporte TI", "10:20", propio = false)
            BurbujaComentario("Gracias, quedo atento.", "Tú", "10:32", propio = true)
            Estrellas(4)
            AyudaTextField("", {}, "Comentario", modifier = Modifier.fillMaxWidth(), placeholder = "Escribe un comentario…")
            AyudaButton("Confirmar", {}, Modifier.fillMaxWidth())
        }
    }
}
