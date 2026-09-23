package com.tuapp.clinica.salud.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuapp.clinica.salud.navigation.Routes

@Composable
fun AppDrawer(
    rutaActual: String,
    onNavegar: (String) -> Unit
) {
    ModalDrawerSheet {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "JP",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(12.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(12.dp))
                Column {
                    Text(
                        text = "José Pérez",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Paciente",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            ItemDrawer(
                icono = Icons.Default.Home,
                titulo = "Inicio",
                seleccionado = rutaActual == Routes.Inicio.route,
                onClick = { onNavegar(Routes.Inicio.route) }
            )
            ItemDrawer(
                icono = Icons.Default.DateRange,
                titulo = "Mis citas",
                seleccionado = rutaActual == Routes.MisCitas.route,
                onClick = { onNavegar(Routes.MisCitas.route) }
            )
            ItemDrawer(
                icono = Icons.AutoMirrored.Filled.List,
                titulo = "Historial médico",
                seleccionado = rutaActual == Routes.Historial.route,
                onClick = { onNavegar(Routes.Historial.route) }
            )
            ItemDrawer(
                icono = Icons.Default.Person,
                titulo = "Perfil",
                seleccionado = rutaActual == Routes.Perfil.route,
                onClick = { onNavegar(Routes.Perfil.route) }
            )
        }
    }
}

@Composable
private fun ItemDrawer(
    icono: ImageVector,
    titulo: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(titulo) },
        icon = { Icon(icono, contentDescription = titulo) },
        selected = seleccionado,
        onClick = onClick,
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}
