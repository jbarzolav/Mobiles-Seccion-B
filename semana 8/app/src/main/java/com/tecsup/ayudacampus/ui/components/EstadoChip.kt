package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.theme.EstadoAbierto
import com.tecsup.ayudacampus.ui.theme.EstadoCerrado
import com.tecsup.ayudacampus.ui.theme.EstadoEnProceso
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto

internal fun EstadoTicket.color(): Color = when (this) {
    EstadoTicket.ABIERTO -> EstadoAbierto
    EstadoTicket.EN_PROCESO -> EstadoEnProceso
    EstadoTicket.RESUELTO -> EstadoResuelto
    EstadoTicket.CERRADO -> EstadoCerrado
}

/** Etiqueta informativa, sin interacción; los filtros usarán FilterChip. */
@Composable
fun EstadoChip(estado: EstadoTicket, modifier: Modifier = Modifier) {
    EtiquetaChip(estado.etiqueta, estado.color(), modifier)
}

@Composable
internal fun EtiquetaChip(texto: String, color: Color, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(50), color = color.copy(alpha = 0.12f), contentColor = color) {
        Text(texto, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), style = MaterialTheme.typography.labelSmall)
    }
}
