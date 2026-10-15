package com.tecsup.ayudacampus.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.theme.PrioridadAlta
import com.tecsup.ayudacampus.ui.theme.PrioridadBaja
import com.tecsup.ayudacampus.ui.theme.PrioridadMedia

internal fun Prioridad.color(): Color = when (this) {
    Prioridad.ALTA -> PrioridadAlta
    Prioridad.MEDIA -> PrioridadMedia
    Prioridad.BAJA -> PrioridadBaja
}

@Composable
fun PrioridadChip(prioridad: Prioridad, modifier: Modifier = Modifier) {
    EtiquetaChip(prioridad.etiqueta, prioridad.color(), modifier)
}
