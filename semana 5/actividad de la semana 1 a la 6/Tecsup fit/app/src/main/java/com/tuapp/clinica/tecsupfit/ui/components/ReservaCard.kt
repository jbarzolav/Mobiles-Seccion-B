package com.tuapp.clinica.tecsupfit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuapp.clinica.tecsupfit.model.EstadoReserva
import com.tuapp.clinica.tecsupfit.model.Reserva

@Composable
fun ReservaCard(
    reserva: Reserva,
    modifier: Modifier = Modifier
) {
    var estadoReserva by remember { mutableStateOf(reserva.estado) }
    val esCancelable = estadoReserva == EstadoReserva.CONFIRMADA

    val colorEstado = when (estadoReserva) {
        EstadoReserva.CONFIRMADA -> MaterialTheme.colorScheme.primary
        EstadoReserva.COMPLETADA -> MaterialTheme.colorScheme.onSurfaceVariant
        EstadoReserva.CANCELADA -> MaterialTheme.colorScheme.error
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = colorEstado
            ) {}
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reserva.clase,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${reserva.fecha}, ${reserva.hora}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = colorEstado.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = estadoReserva.etiqueta,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = colorEstado
                    )
                }
            }
            TextButton(
                onClick = {
                    if (esCancelable) {
                        estadoReserva = EstadoReserva.CANCELADA
                    }
                },
                enabled = esCancelable
            ) {
                Text("Cancelar")
            }
        }
    }
}
