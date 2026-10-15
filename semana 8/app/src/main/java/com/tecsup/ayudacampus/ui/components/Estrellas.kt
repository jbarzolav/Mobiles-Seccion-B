package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.theme.EstrellaActiva
import kotlin.math.cos
import kotlin.math.sin

/** 0 significa sin calificar; al pulsar se emiten valores del 1 al 5. Estado elevado al consumidor. */
@Composable
fun Estrellas(
    calificacion: Int,
    modifier: Modifier = Modifier,
    onCalificacionChange: ((Int) -> Unit)? = null,
    enabled: Boolean = true
) {
    require(calificacion in 0..5) { "La calificación debe estar entre 0 y 5" }
    val inactiva = MaterialTheme.colorScheme.outlineVariant
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.selectableGroup()) {
            for (valor in 1..5) {
                val interaccion = if (onCalificacionChange != null) Modifier.selectable(
                    selected = valor == calificacion, enabled = enabled, role = Role.RadioButton,
                    onClick = { onCalificacionChange(valor) }
                ) else Modifier
                Box(Modifier.size(48.dp).then(interaccion).semantics {
                    contentDescription = "$valor de 5 estrellas"
                }, contentAlignment = Alignment.Center) {
                    Canvas(Modifier.size(28.dp)) {
                        val path = Path()
                        repeat(10) { punto ->
                            val angulo = Math.PI * punto / 5 - Math.PI / 2
                            val radio = size.minDimension / 2 * if (punto % 2 == 0) 1f else 0.44f
                            val x = center.x + cos(angulo).toFloat() * radio
                            val y = center.y + sin(angulo).toFloat() * radio
                            if (punto == 0) path.moveTo(x, y) else path.lineTo(x, y)
                        }
                        path.close()
                        drawPath(path, if (valor <= calificacion) EstrellaActiva else inactiva)
                    }
                }
            }
        }
        Text(if (calificacion == 0) "Sin calificar" else "$calificacion de 5 estrellas",
            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
