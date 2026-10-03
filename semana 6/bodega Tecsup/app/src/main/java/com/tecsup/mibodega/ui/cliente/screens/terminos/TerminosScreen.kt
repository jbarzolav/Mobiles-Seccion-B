package com.tecsup.mibodega.ui.cliente.screens.terminos

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.BodegaTheme

private val seccionesTerminos = listOf(
    "Uso de la cuenta" to
        "Los datos que registras (nombre, teléfono y dirección) se usan " +
        "únicamente para preparar y entregar tus pedidos dentro de Mi Bodega.",
    "Pedidos y delivery" to
        "Cada pedido muestra el subtotal, el costo de delivery y el total " +
        "antes de confirmar. Al confirmar recibes un número de pedido y el " +
        "detalle de la entrega.",
    "Precios y productos" to
        "Los precios y la disponibilidad de los productos pueden cambiar sin " +
        "previo aviso. El total se recalcula en pantalla al modificar cantidades.",
    "Cancelaciones" to
        "Puedes quitar productos del carrito antes de confirmar el pedido. " +
        "Una vez confirmado, contáctanos al correo de soporte para cambios."
)

/**
 * Pantalla: Términos y condiciones.
 * El TODO del esqueleto decía "abrir términos y condiciones"; en lugar de un
 * diálogo se creó una pantalla con ruta propia (TERMINOS) para que la flecha
 * de volver regrese a Bienvenida con popBackStack, igual que el resto de la app.
 *
 * @param onVolver regresa a la pantalla anterior, lo decide ClienteApp
 */
@Composable
fun TerminosScreen(onVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoTerminos(onVolver = onVolver)

        Spacer(Modifier.height(16.dp))

        seccionesTerminos.forEachIndexed { index, (titulo, contenido) ->
            Text(
                text = "${index + 1}. $titulo",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = contenido,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.height(8.dp))

        BotonPrimario(
            texto = "Entendido",
            onClick = onVolver
        )

        Spacer(Modifier.height(24.dp))
    }
}

// Sub-composables PRIVADOS: solo los usa esta pantalla.

@Composable
private fun EncabezadoTerminos(onVolver: () -> Unit) {
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
            text = "Términos y condiciones",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(Modifier.size(48.dp)) // balancea el ancho del ícono de la izquierda
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TerminosPreview() {
    BodegaTheme {
        TerminosScreen(onVolver = {})
    }
}
