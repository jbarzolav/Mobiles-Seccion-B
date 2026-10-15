package com.tecsup.ayudacampus.ui.components.graficos

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import java.text.NumberFormat

/** Datos de presentación compartidos por ambos gráficos. Color opcional para categorías semánticas. */
data class DatoGrafico(val etiqueta: String, val valor: Float, val color: Color? = null) {
    init { require(valor.isFinite() && valor >= 0f) { "El valor debe ser finito y no negativo" } }
}

internal fun formatoValor(valor: Number): String = NumberFormat.getNumberInstance().format(valor)

@Composable
internal fun coloresGrafico(): List<Color> = with(MaterialTheme.colorScheme) {
    listOf(primary, secondary, tertiary, primaryContainer, secondaryContainer, tertiaryContainer)
}

@Composable
fun GraficoDona(
    datos: List<DatoGrafico>,
    modifier: Modifier = Modifier,
    titulo: String = "Tickets por estado"
) {
    val paleta = coloresGrafico()
    val total = datos.sumOf { it.valor.toDouble() }
    val vacio = MaterialTheme.colorScheme.outlineVariant
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(titulo, style = MaterialTheme.typography.titleSmall)
        Box(Modifier.fillMaxWidth().height(180.dp), contentAlignment = Alignment.Center) {
            Canvas(Modifier.fillMaxSize().semantics {
                contentDescription = if (total == 0.0) "Sin datos" else datos.joinToString { "${it.etiqueta}: ${it.valor}" }
            }) {
                val grosor = 24.dp.toPx().coerceAtMost(size.minDimension / 4)
                val diametro = (size.minDimension - grosor).coerceAtLeast(0f)
                val origen = Offset((size.width - diametro) / 2, (size.height - diametro) / 2)
                if (total == 0.0) drawCircle(vacio, diametro / 2, style = Stroke(grosor))
                else {
                    var inicio = -90f
                    datos.forEachIndexed { indice, dato ->
                        val barrido = (dato.valor / total * 360).toFloat()
                        drawArc(dato.color ?: paleta[indice % paleta.size], inicio, barrido, false,
                            origen, Size(diametro, diametro), style = Stroke(grosor))
                        inicio += barrido
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(if (total == 0.0) "Sin datos" else formatoValor(total), style = MaterialTheme.typography.titleLarge)
                Text("Total", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        datos.forEachIndexed { indice, dato ->
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(Modifier.size(10.dp).background(dato.color ?: paleta[indice % paleta.size], CircleShape))
                Text(dato.etiqueta, Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                Text(formatoValor(dato.valor), style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 340)
@Composable
private fun GraficoDonaPreview() {
    AyudaTheme(darkTheme = false) {
        GraficoDona(listOf(DatoGrafico("Abiertos", 3f), DatoGrafico("Resueltos", 5f)), Modifier.padding(16.dp))
    }
}
