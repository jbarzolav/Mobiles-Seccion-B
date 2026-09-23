package com.tuapp.clinica.salud.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EspecialidadChips(
    especialidades: List<String>,
    seleccionada: String,
    onSeleccionar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(especialidades) { especialidad ->
            FilterChip(
                selected = seleccionada == especialidad,
                onClick = { onSeleccionar(especialidad) },
                label = { Text(especialidad) },
                shape = RoundedCornerShape(16.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    selectedLabelColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
