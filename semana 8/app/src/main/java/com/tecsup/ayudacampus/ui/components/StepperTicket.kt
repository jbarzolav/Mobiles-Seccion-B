package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto

/** Progreso visual únicamente; no decide ni modifica transiciones de estado. */
@Composable
fun StepperTicket(estado: EstadoTicket, modifier: Modifier = Modifier) {
    val estados = EstadoTicket.entries
    val actual = estados.indexOf(estado)
    val activo = MaterialTheme.colorScheme.primary
    val pendiente = MaterialTheme.colorScheme.outlineVariant
    val superficie = MaterialTheme.colorScheme.surface
    Column(modifier.semantics(mergeDescendants = true) { contentDescription = "Estado del ticket: ${estado.etiqueta}" }) {
        Canvas(Modifier.fillMaxWidth().height(32.dp)) {
            val paso = size.width / estados.size
            val radio = 7.dp.toPx()
            for (i in 0 until estados.lastIndex) {
                drawLine(if (i < actual) activo else pendiente,
                    Offset(paso * (i + 0.5f), center.y), Offset(paso * (i + 1.5f), center.y), 2.dp.toPx())
            }
            estados.forEachIndexed { i, _ ->
                val centro = Offset(paso * (i + 0.5f), center.y)
                if (i == actual) drawCircle(activo.copy(alpha = 0.15f), radio + 4.dp.toPx(), centro)
                drawCircle(when { i < actual -> EstadoResuelto; i == actual -> activo; else -> superficie }, radio, centro)
                if (i > actual) drawCircle(pendiente, radio, centro, style = Stroke(2.dp.toPx()))
                if (i < actual) {
                    drawLine(superficie, centro + Offset(-3.dp.toPx(), 0f), centro + Offset(-1.dp.toPx(), 2.dp.toPx()), 2.dp.toPx())
                    drawLine(superficie, centro + Offset(-1.dp.toPx(), 2.dp.toPx()), centro + Offset(3.dp.toPx(), -3.dp.toPx()), 2.dp.toPx())
                }
            }
        }
        Row(Modifier.fillMaxWidth()) {
            estados.forEachIndexed { i, paso ->
                Text(paso.etiqueta, Modifier.weight(1f), textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (i <= actual) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
