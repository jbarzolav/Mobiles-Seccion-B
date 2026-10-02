package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Datos que el cliente entrega para que llegue el pedido.
 * Se define aquí (y no dentro del composable) porque la viaja de
 * DatosEntregaScreen hacia ConfirmacionScreen a través de ClienteApp.
 */
data class DatosEntrega(
    val nombre: String,
    val telefono: String,
    val direccion: String,
    val referencia: String,
    val horario: String
)

private val horariosDisponibles = listOf(
    "Hoy 6:00 - 9:00 pm",
    "Mañana 8:00 - 12:00 am",
    "Mañana 2:00 - 5:00 pm"
)

/**
 * Pantalla 6: Datos de entrega (mockup "Cliente").
 * Igual que Registro, guarda su estado de formulario con remember y al
 * confirmar entrega un DatosEntrega listo hacia arriba; el resto de la
 * navegación (y el popUpTo) lo decide ClienteApp.
 *
 * @param onConfirmarPedido recibe el formulario completo ya validado
 */
@Composable
fun DatosEntregaScreen(
    onVolver: () -> Unit,
    onConfirmarPedido: (DatosEntrega) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf(horariosDisponibles.first()) }

    val formularioListo = nombre.isNotBlank() && telefono.isNotBlank() && direccion.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoEntrega(onVolver = onVolver)

        Spacer(Modifier.height(20.dp))

        ResumenEntrega(direccion = direccion, horario = horario)

        Spacer(Modifier.height(20.dp))

        CampoTexto(
            etiqueta = "Nombre de quien recibe",
            valor = nombre,
            onValorCambia = { nombre = it },
            placeholder = "Juan Pérez"
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefono,
            onValorCambia = { telefono = it },
            placeholder = "987 654 321",
            teclado = KeyboardType.Phone
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Dirección de entrega",
            valor = direccion,
            onValorCambia = { direccion = it },
            placeholder = "Av. Los Olivos 123"
        )
        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Referencia",
            valor = referencia,
            onValorCambia = { referencia = it },
            placeholder = "Frente al parque"
        )

        Spacer(Modifier.height(20.dp))

        SelectorHorario(
            horarioSeleccionado = horario,
            onHorarioSeleccionado = { horario = it }
        )

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Confirmar pedido",
            onClick = {
                onConfirmarPedido(
                    DatosEntrega(
                        nombre = nombre,
                        telefono = telefono,
                        direccion = direccion,
                        referencia = referencia,
                        horario = horario
                    )
                )
            },
            habilitado = formularioListo
        )

        Spacer(Modifier.height(24.dp))
    }
}

// Sub-composables PRIVADOS: solo los usa esta pantalla.

@Composable
private fun EncabezadoEntrega(onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        Text(
            text = "Datos de entrega",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(Modifier.size(48.dp)) // balancea el ancho del ícono de la izquierda
    }
    Text(
        text = "¿A dónde enviamos tu pedido?",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth(),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
    )
}

@Composable
private fun ResumenEntrega(direccion: String, horario: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrisClaro, MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = "Resumen de la entrega",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = VerdeBodega
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Dirección",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = direccion.ifBlank { "Sin dirección todavía" },
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Horario",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = horario,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SelectorHorario(
    horarioSeleccionado: String,
    onHorarioSeleccionado: (String) -> Unit
) {
    Text(
        text = "Horario de entrega",
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.SemiBold
    )
    Spacer(Modifier.height(8.dp))
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        horariosDisponibles.forEach { horario ->
            FilterChip(
                selected = horario == horarioSeleccionado,
                onClick = { onHorarioSeleccionado(horario) },
                label = { Text(horario) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(
            onVolver = {},
            onConfirmarPedido = {}
        )
    }
}
