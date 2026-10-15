package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

@Composable
fun CategoriasScreen(vm: CategoriasViewModel, onEditar: (Int) -> Unit, modifier: Modifier = Modifier) {
    val categorias by vm.categorias.collectAsStateWithLifecycle()
    LazyColumn(modifier = modifier) {
        items(categorias, key = { it.id }) { c ->
            CategoriaItem(c, onToggle = { vm.alternar(c) }, onEditar = { onEditar(c.id) })
        }
    }
}

/** La fila solo presenta la categoría y comunica acciones al consumidor. */
@Composable
private fun CategoriaItem(c: Categoria, onToggle: () -> Unit, onEditar: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(Modifier.weight(1f)) {
            Text(c.nombre, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground)
            Text(if (c.activa) "Activa" else "Inactiva", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = c.activa,
            onCheckedChange = { onToggle() },
            modifier = Modifier.semantics { contentDescription = "Activar categoría ${c.nombre}" }
        )
        TextButton(onClick = onEditar) { Text("Editar") }
    }
}
