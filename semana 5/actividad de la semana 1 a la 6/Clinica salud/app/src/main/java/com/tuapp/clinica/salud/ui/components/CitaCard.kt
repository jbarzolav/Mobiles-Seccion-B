package com.tuapp.clinica.salud.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuapp.clinica.salud.model.Cita
import com.tuapp.clinica.salud.model.EstadoCita

@Composable
fun CitaCard(
    cita: Cita,
    modifier: Modifier = Modifier,
    onCancelar: (() -> Unit)? = null
) {
    val colorEstado = when (cita.estado) {
        EstadoCita.CONFIRMADA -> MaterialTheme.colorScheme.primary
        EstadoCita.COMPLETADA -> MaterialTheme.colorScheme.outline
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
                    .padding(end = 12.dp)
                    .fillMaxWidth(0f)
                    .padding(0.dp),
                color = colorEstado
            ) {}
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = cita.medico,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${cita.fecha}, ${cita.hora}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Surface(
                    modifier = Modifier.padding(top = 6.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = colorEstado
                ) {
                    Text(
                        text = cita.estado.etiqueta,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            if (cita.estado == EstadoCita.CONFIRMADA && onCancelar != null) {
                OutlinedButton(
                    onClick = onCancelar,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}
