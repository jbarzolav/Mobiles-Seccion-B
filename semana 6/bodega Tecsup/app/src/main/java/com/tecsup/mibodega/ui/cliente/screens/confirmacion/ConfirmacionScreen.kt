package com.tecsup.mibodega.ui.cliente.screens.confirmacion

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntrega
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

private const val COSTO_DELIVERY = 4.00

/**
 * Pantalla 7: Confirmación del pedido (mockup "Cliente").
 * Es el final del flujo: acá no hay formulario, solo se muestran los datos
 * que llegaron desde DatosEntregaScreen y el total del carrito.
 * El botón "Volver al inicio" lo decide ClienteApp con popUpTo para que la
 * flecha atrás no regrese a la entrega.
 *
 * @param subtotal suma de los productos, el delivery se agrega aquí
 * @param onVolverInicio navegación de salida, la define ClienteApp
 */
@Composable
fun ConfirmacionScreen(
    datos: DatosEntrega,
    subtotal: Double,
    onVolverInicio: () -> Unit
) {
    val total = subtotal + COSTO_DELIVERY
    // Número de pedido ficticio, solo vive mientras está esta pantalla en memoria.
    val numeroPedido = remember { (1000..9999).random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = VerdeBodega,
            modifier = Modifier.size(96.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Pedido confirmado",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Gracias por tu compra, ${datos.nombre.ifBlank { "cliente" }}\n" +
                "Pedido #$numeroPedido",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(28.dp))

        EntregaConfirmada(datos = datos)

        Spacer(Modifier.height(16.dp))

        PagoConfirmado(subtotal = subtotal, total = total)

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Volver al inicio",
            onClick = onVolverInicio
        )

        Spacer(Modifier.height(24.dp))
    }
}

// Sub-composables PRIVADOS: solo los usa esta pantalla.

@Composable
private fun EntregaConfirmada(datos: DatosEntrega) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrisClaro, MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = "Datos de entrega",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = VerdeBodega
        )
        Spacer(Modifier.height(12.dp))

        FilaDato(etiqueta = "Recibe", valor = datos.nombre)
        FilaDato(etiqueta = "Teléfono", valor = datos.telefono)
        FilaDato(etiqueta = "Dirección", valor = datos.direccion)
        FilaDato(etiqueta = "Referencia", valor = datos.referencia.ifBlank { "—" })
        FilaDato(etiqueta = "Horario", valor = datos.horario)
    }
}

@Composable
private fun PagoConfirmado(subtotal: Double, total: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrisClaro, MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = "Resumen del pago",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = VerdeBodega
        )
        Spacer(Modifier.height(12.dp))

        FilaImporte(etiqueta = "Subtotal", valor = subtotal)
        FilaImporte(etiqueta = "Costo de delivery", valor = COSTO_DELIVERY)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total pagado",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "S/ %.2f".format(total),
                style = MaterialTheme.typography.titleMedium,
                color = VerdeBodega
            )
        }
    }
}

@Composable
private fun FilaDato(etiqueta: String, valor: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FilaImporte(etiqueta: String, valor: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = "S/ %.2f".format(valor), color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ConfirmacionPreview() {
    BodegaTheme {
        ConfirmacionScreen(
            datos = DatosEntrega(
                nombre = "Juan Pérez",
                telefono = "987 654 321",
                direccion = "Av. Los Olivos 123",
                referencia = "Frente al parque",
                horario = "Hoy 6:00 - 9:00 pm"
            ),
            subtotal = 45.00,
            onVolverInicio = {}
        )
    }
}
