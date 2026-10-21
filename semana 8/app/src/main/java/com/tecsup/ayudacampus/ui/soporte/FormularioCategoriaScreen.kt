package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

data class IconoOpcion(val clave: String, val icono: ImageVector)

private val opcionesIconos = listOf(
    IconoOpcion("pc", Icons.Outlined.Computer),
    IconoOpcion("code", Icons.Outlined.Code),
    IconoOpcion("wifi", Icons.Outlined.Wifi),
    IconoOpcion("key", Icons.Outlined.Key),
    IconoOpcion("printer", Icons.Outlined.Print),
    IconoOpcion("tag", Icons.AutoMirrored.Outlined.Label)
)

@Composable
fun FormularioCategoriaScreen(
    vm: CategoriasViewModel,
    categoriaId: Int? = null,
    onVolver: () -> Unit
) {
    val categorias by vm.categorias.collectAsStateWithLifecycle()
    val categoriaExistente = remember(categorias, categoriaId) {
        if (categoriaId != null && categoriaId != 0) {
            categorias.find { it.id == categoriaId }
        } else null
    }

    var nombre by remember(categoriaExistente) { mutableStateOf(categoriaExistente?.nombre.orEmpty()) }
    var iconoSeleccionado by remember(categoriaExistente) { mutableStateOf(categoriaExistente?.icono ?: "tag") }
    var activa by remember(categoriaExistente) { mutableStateOf(categoriaExistente?.activa ?: true) }
    var errorNombre by remember { mutableStateOf<String?>(null) }

    val esEdicion = categoriaExistente != null
    val titulo = if (esEdicion) "Editar categoría" else "Nueva categoría"

    Scaffold(
        topBar = { AppTopBar(titulo, onVolver = onVolver) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Nombre de la categoría
            AyudaTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    if (errorNombre != null) errorNombre = null
                },
                label = "Nombre de la categoría",
                placeholder = "Ej. Laboratorios",
                isError = errorNombre != null,
                supportingText = errorNombre,
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de Ícono
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Ícono", style = MaterialTheme.typography.titleSmall)
                // Grid 3x2
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    val filas = opcionesIconos.chunked(3)
                    filas.forEach { fila ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            fila.forEach { item ->
                                val seleccionado = iconoSeleccionado == item.clave
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(64.dp)
                                        .clickable { iconoSeleccionado = item.clave },
                                    shape = MaterialTheme.shapes.medium,
                                    color = if (seleccionado) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                                    else MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(
                                        if (seleccionado) 2.dp else 1.dp,
                                        if (seleccionado) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.outlineVariant
                                    )
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = item.icono,
                                            contentDescription = item.clave,
                                            tint = if (seleccionado) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Categoría activa
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Categoría activa", style = MaterialTheme.typography.titleSmall)
                    Text(
                        "Los estudiantes podrán elegirla",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = activa,
                    onCheckedChange = { activa = it }
                )
            }

            // Vista previa
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Vista previa", style = MaterialTheme.typography.titleSmall)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                            modifier = Modifier.size(44.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = iconoDe(iconoSeleccionado),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Column {
                            Text(
                                text = if (nombre.isNotBlank()) nombre else "Nombre de la categoría",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "0 tickets",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Botones de Acción
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                AyudaButton(
                    texto = "Guardar categoría",
                    onClick = {
                        if (nombre.isBlank()) {
                            errorNombre = "El nombre es obligatorio"
                        } else {
                            val c = Categoria(
                                id = categoriaExistente?.id ?: 0,
                                nombre = nombre,
                                icono = iconoSeleccionado,
                                activa = activa
                            )
                            vm.guardar(c) { onVolver() }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = onVolver,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 48.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Cancelar")
                }

                if (esEdicion) {
                    TextButton(
                        onClick = {
                            categoriaExistente?.let { cat ->
                                vm.eliminar(cat) { onVolver() }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar categoría")
                    }
                }
            }
        }
    }
}

private fun iconoDe(icono: String): ImageVector = when (icono) {
    "pc" -> Icons.Outlined.Computer
    "code" -> Icons.Outlined.Code
    "wifi" -> Icons.Outlined.Wifi
    "key" -> Icons.Outlined.Key
    "printer" -> Icons.Outlined.Print
    else -> Icons.AutoMirrored.Outlined.Label
}
