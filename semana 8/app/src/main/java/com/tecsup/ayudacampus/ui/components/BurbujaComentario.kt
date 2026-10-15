package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BurbujaComentario(
    mensaje: String,
    autor: String,
    hora: String,
    propio: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = if (propio) Arrangement.End else Arrangement.Start) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp,
                bottomEnd = if (propio) 4.dp else 16.dp, bottomStart = if (propio) 16.dp else 4.dp),
            color = if (propio) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = if (propio) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("$autor · $hora", style = MaterialTheme.typography.labelSmall)
                Text(mensaje, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
