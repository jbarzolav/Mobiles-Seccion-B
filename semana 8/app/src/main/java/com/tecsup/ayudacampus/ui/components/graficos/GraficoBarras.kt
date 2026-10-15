package com.tecsup.ayudacampus.ui.components.graficos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.theme.AyudaTheme

/** Barras horizontales: admite cualquier cantidad de categorías sin comprimir sus etiquetas. */
@Composable
fun GraficoBarras(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    titulo: String = "Tickets por categoría"
) {
    val paleta = coloresGrafico()
    val maximo = datos.maxOfOrNull { it.valor } ?: 0f
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(titulo, style = MaterialTheme.typography.titleSmall)
        if (datos.isEmpty() || maximo == 0f) {
            Text("Sin datos", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        datos.forEachIndexed { indice, dato ->
            Column(verticalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.semantics(mergeDescendants = true) {
                contentDescription = "${dato.etiqueta}: ${dato.valor}"
            }) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(dato.etiqueta, Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                    Text(formatoValor(dato.valor), style = MaterialTheme.typography.labelMedium)
                }
                Box(Modifier.fillMaxWidth().height(16.dp).clip(MaterialTheme.shapes.extraSmall)
                    .background(MaterialTheme.colorScheme.surfaceVariant)) {
                    val fraccion = if (maximo > 0f) dato.valor / maximo else 0f
                    if (fraccion > 0f) {
                        Box(Modifier.fillMaxWidth(fraccion).fillMaxHeight().clip(MaterialTheme.shapes.extraSmall)
                            .background(dato.color ?: paleta[indice % paleta.size]))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 340)
@Composable
private fun GraficoBarrasPreview() {
    AyudaTheme(Rol.ADMIN, darkTheme = false) {
        GraficoBarras(listOf(DatoGrafico("Hardware", 8f), DatoGrafico("Red / Wi-Fi", 5f), DatoGrafico("Impresión", 3f)), Modifier.padding(16.dp))
    }
}
