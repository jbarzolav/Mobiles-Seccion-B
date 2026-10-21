package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.graficos.DatoGrafico
import com.tecsup.ayudacampus.ui.components.graficos.GraficoBarras
import com.tecsup.ayudacampus.ui.components.graficos.GraficoDona
import com.tecsup.ayudacampus.ui.theme.EstadoAbierto
import com.tecsup.ayudacampus.ui.theme.EstadoEnProceso
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto
import com.tecsup.ayudacampus.viewmodel.PanelViewModel

@Composable
fun PanelScreen(
    vm: PanelViewModel,
    onVerAltas: () -> Unit,
    onTickets: () -> Unit,
    onCategorias: () -> Unit,
    onAjustes: () -> Unit
) {
    val abiertos by vm.abiertos.collectAsStateWithLifecycle()
    val enProceso by vm.enProceso.collectAsStateWithLifecycle()
    val resueltos by vm.resueltos.collectAsStateWithLifecycle()
    val porEstado by vm.porEstado.collectAsStateWithLifecycle()
    val porCategoria by vm.porCategoria.collectAsStateWithLifecycle()
    val altasSinAtender by vm.altasSinAtender.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AppTopBar("Panel de soporte", actions = {
                IconButton(onClick = {}, modifier = Modifier.semantics { contentDescription = "Notificaciones" }) {
                    Icon(Icons.Outlined.Notifications, null)
                }
            })
        },
        bottomBar = {
            BarraSoporte("panel", onPanel = {}, onTickets = onTickets,
                onCategorias = onCategorias, onAjustes = onAjustes)
        }
    ) { padding ->
        LazyColumn(
            Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(top = 4.dp, bottom = 20.dp)
        ) {
            item {
                Text("Mesa de ayuda TI · Hoy", style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    ContadorColored(abiertos, "Abiertos", EstadoAbierto, Modifier.weight(1f))
                    ContadorColored(enProceso, "En proceso", EstadoEnProceso, Modifier.weight(1f))
                    ContadorColored(resueltos, "Resueltos", EstadoResuelto, Modifier.weight(1f))
                }
            }
            item { GraficoDona(porEstado, Modifier.fillMaxWidth()) }
            item { GraficoBarras(porCategoria, Modifier.fillMaxWidth()) }
            if (altasSinAtender > 0) {
                item { AlertaAltas(altasSinAtender, onVerAltas, Modifier.fillMaxWidth()) }
            }
        }
    }
}

@Composable
private fun ContadorColored(valor: Int, etiqueta: String, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.semantics { contentDescription = "$valor $etiqueta" },
        shape = MaterialTheme.shapes.medium,
        color = color.copy(alpha = 0.12f)
    ) {
        Column(Modifier.fillMaxWidth().padding(vertical = 14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(valor.toString(), style = MaterialTheme.typography.headlineSmall,
                color = color, fontWeight = FontWeight.Bold)
            Text(etiqueta, style = MaterialTheme.typography.labelSmall, color = color)
        }
    }
}

@Composable
private fun AlertaAltas(cantidad: Int, onVer: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.4f)),
        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.45f)
    ) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Icon(Icons.Outlined.Warning, null, tint = MaterialTheme.colorScheme.error)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(if (cantidad == 1) "1 ticket de prioridad Alta"
                     else "$cantidad tickets de prioridad Alta",
                    style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                Text("sin atender hace más de 1 hora",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Button(onClick = onVer, colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError)) {
                Text("Ver")
            }
        }
    }
}

/** Barra de navegación del rol Soporte TI; duplicada a propósito para no crear archivos fuera del paso. */
@Composable
internal fun BarraSoporte(
    actual: String,
    onPanel: () -> Unit,
    onTickets: () -> Unit,
    onCategorias: () -> Unit,
    onAjustes: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(actual == "panel", onPanel,
            icon = { Icon(Icons.Outlined.BarChart, null) }, label = { Text("Panel") })
        NavigationBarItem(actual == "tickets", onTickets,
            icon = { Icon(Icons.AutoMirrored.Outlined.List, null) }, label = { Text("Tickets") })
        NavigationBarItem(actual == "categorias", onCategorias,
            icon = { Icon(Icons.AutoMirrored.Outlined.Label, null) }, label = { Text("Categorías") })
        NavigationBarItem(actual == "ajustes", onAjustes,
            icon = { Icon(Icons.Outlined.Settings, null) }, label = { Text("Ajustes") })
    }
}
