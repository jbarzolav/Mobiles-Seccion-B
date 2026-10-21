package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
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
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

private val ColorEstudiantePri = Color(0xFF0284C7)
private val ColorEstudianteFondo = Color(0xFFE0F2FE)

@Composable
fun PerfilScreen(
    usuario: Usuario,
    sesion: SesionViewModel,
    repo: AyudaRepository,
    onTickets: () -> Unit,
    onNuevo: () -> Unit,
    onCambioPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val modoOscuro by sesion.modoOscuro.collectAsStateWithLifecycle()
    val notificaciones by sesion.notificaciones.collectAsStateWithLifecycle()
    val ticketsCliente by repo.observarTicketsCliente(usuario.id).collectAsStateWithLifecycle(emptyList())

    val abiertos = remember(ticketsCliente) {
        ticketsCliente.count {
            it.ticket.estado == EstadoTicket.ABIERTO.name || it.ticket.estado == EstadoTicket.EN_PROCESO.name
        }
    }
    val resueltos = remember(ticketsCliente) {
        ticketsCliente.count {
            it.ticket.estado == EstadoTicket.RESUELTO.name || it.ticket.estado == EstadoTicket.CERRADO.name
        }
    }
    val total = remember(ticketsCliente) { ticketsCliente.size }

    Scaffold(
        topBar = { AppTopBar("Perfil") },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(false, onTickets, icon = { Icon(Icons.AutoMirrored.Outlined.List, null) }, label = { Text("Tickets") })
                NavigationBarItem(false, onNuevo, icon = { Icon(Icons.Outlined.Add, null) }, label = { Text("Nuevo") })
                NavigationBarItem(true, {}, icon = { Icon(Icons.Outlined.Person, null) }, label = { Text("Perfil") })
            }
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
            // Card Datos del Estudiante
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
                        color = ColorEstudianteFondo,
                        modifier = Modifier.size(56.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Outlined.Person,
                                contentDescription = null,
                                tint = ColorEstudiantePri,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(usuario.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(usuario.correo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = ColorEstudianteFondo,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Text(
                                "Estudiante",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = ColorEstudiantePri
                            )
                        }
                    }
                }
            }

            // Contadores de Tickets
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Abiertos
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    color = Color(0xFFFEF3C7)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text("$abiertos", style = MaterialTheme.typography.titleLarge, color = Color(0xFFD97706), fontWeight = FontWeight.Bold)
                        Text("Abiertos", style = MaterialTheme.typography.labelMedium, color = Color(0xFF92400E))
                    }
                }
                // Resueltos
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    color = Color(0xFFDCFCE7)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text("$resueltos", style = MaterialTheme.typography.titleLarge, color = Color(0xFF16A34A), fontWeight = FontWeight.Bold)
                        Text("Resueltos", style = MaterialTheme.typography.labelMedium, color = Color(0xFF166534))
                    }
                }
                // Total
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    color = ColorEstudianteFondo
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text("$total", style = MaterialTheme.typography.titleLarge, color = ColorEstudiantePri, fontWeight = FontWeight.Bold)
                        Text("Total", style = MaterialTheme.typography.labelMedium, color = Color(0xFF075985))
                    }
                }
            }

            // Banner Cambiar de Perfil
            Surface(
                onClick = { sesion.cambiarPerfil { onCambioPerfil() } },
                shape = MaterialTheme.shapes.medium,
                color = ColorEstudiantePri,
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
                        Text("Modo práctica: ir a vista Soporte TI", style = MaterialTheme.typography.bodySmall, color = Color.White.copy(alpha = 0.85f))
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
                                Text("Avisar cambios de estado", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Switch(checked = notificaciones, onCheckedChange = { sesion.cambiarNotificaciones(it) })
                        }
                    }
                }

                // DataStore info box
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = Color(0xFFF0F9FF),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Se guardan en el dispositivo", style = MaterialTheme.typography.labelSmall, color = ColorEstudiantePri)
                        Text("con DataStore", style = MaterialTheme.typography.labelSmall, color = ColorEstudiantePri, fontWeight = FontWeight.Bold)
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
