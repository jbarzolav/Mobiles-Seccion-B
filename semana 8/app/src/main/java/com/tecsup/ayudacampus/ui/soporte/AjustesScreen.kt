package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

private val ColorSoportePri = Color(0xFF7C3AED)
private val ColorSoporteFondo = Color(0xFFF3E8FF)

@Composable
fun AjustesScreen(
    usuario: Usuario,
    sesion: SesionViewModel,
    onPanel: () -> Unit,
    onTickets: () -> Unit,
    onCategorias: () -> Unit,
    onCambioPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val modoOscuro by sesion.modoOscuro.collectAsStateWithLifecycle()
    val notificaciones by sesion.notificaciones.collectAsStateWithLifecycle()
    val recibirTickets by sesion.recibirTickets.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { AppTopBar("Ajustes") },
        bottomBar = {
            BarraSoporte("ajustes", onPanel = onPanel, onTickets = onTickets,
                onCategorias = onCategorias, onAjustes = {})
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card Datos de Soporte TI
            Surface(
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = ColorSoporteFondo,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = null,
                                tint = ColorSoportePri,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(usuario.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(usuario.correo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = ColorSoporteFondo,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                "Perfil activo",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = ColorSoportePri
                            )
                        }
                    }
                }
            }

            // Banner Cambiar de Perfil
            Surface(
                onClick = { sesion.cambiarPerfil { onCambioPerfil() } },
                shape = MaterialTheme.shapes.medium,
                color = ColorSoportePri,
                contentColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(Icons.Outlined.SwapHoriz, contentDescription = null, modifier = Modifier.size(28.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Cambiar de perfil", style = MaterialTheme.typography.titleSmall, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Modo práctica: ir a vista Estudiante", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.85f))
                    }
                    Icon(Icons.Outlined.ChevronRight, contentDescription = null)
                }
            }

            // Sección Preferencias
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Preferencias", style = MaterialTheme.typography.titleSmall)
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        // Modo oscuro
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Modo oscuro", style = MaterialTheme.typography.titleSmall)
                                Text("Cambia el tema de la app", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = modoOscuro, onCheckedChange = { sesion.cambiarModoOscuro(it) })
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        // Notificaciones
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Notificaciones", style = MaterialTheme.typography.titleSmall)
                                Text("Avisar tickets nuevos", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = notificaciones, onCheckedChange = { sesion.cambiarNotificaciones(it) })
                        }
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                        // Recibir tickets
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Recibir tickets", style = MaterialTheme.typography.titleSmall)
                                Text("Abrir o cerrar la mesa de ayuda", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = recibirTickets, onCheckedChange = { sesion.cambiarRecibirTickets(it) })
                        }
                    }
                }

                // DataStore info box
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = Color(0xFFFAF5FF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Se guardan en el dispositivo", style = MaterialTheme.typography.labelSmall, color = ColorSoportePri)
                        Text("con DataStore", style = MaterialTheme.typography.labelSmall, color = ColorSoportePri, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // Botón Cerrar Sesión
            OutlinedButton(
                onClick = { sesion.cerrarSesion { onCerrarSesion() } },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
