package com.tuapp.navlab.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuapp.navlab.navigation.Screen
import com.tuapp.navlab.ui.theme.getHeaderGradient

/**
 * Pantalla "Configuración de Perfil". Mantiene la firma de función `ProfileScreen(navController)`
 * e incluye las secciones "INFORMACIÓN PERSONAL" y "ACADÉMICO" con iconos y botón "Cerrar Sesión".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val isDark = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            // Header superior con degradado institucional
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .background(getHeaderGradient(isDark))
                    .statusBarsPadding()
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(48.dp) // Área de toque accesible mínima
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a la pantalla anterior",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Configuración de Perfil",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Resumen superior del perfil con avatar
            Card(
                shape = RoundedCornerShape(16.dp), // Bordes redondeados consistentes de 16.dp
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(68.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Avatar de Jose Barzola",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Jose Barzola",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Diseño y Desarrollo de Software",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sección 1: INFORMACIÓN PERSONAL
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "INFORMACIÓN PERSONAL",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.Person,
                        label = "Nombre Completo",
                        value = "Jose Barzola"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.Email,
                        label = "Correo Institucional",
                        value = "j.barzola@tecsup.edu.pe"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.Phone,
                        label = "Teléfono Móvil",
                        value = "+51 987 654 321"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.Badge,
                        label = "Documento DNI",
                        value = "72839401"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sección 2: ACADÉMICO
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "ACADÉMICO",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.School,
                        label = "Carrera Profesional",
                        value = "Diseño y Desarrollo de Software"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.DateRange,
                        label = "Ciclo Actual",
                        value = "V Ciclo (2025-1)"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.Star,
                        label = "Promedio Ponderado",
                        value = "17.8 / 20.0 (Tercio Superior)"
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    ProfileDetailRow(
                        icon = Icons.Default.Verified,
                        label = "Estado Académico",
                        value = "Alumno Regular Activo"
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Botón principal de "Cerrar Sesión" con navegación limpia a Screen.Home.route
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        // Limpia la pila de navegación para evitar apilar pantallas repetidas
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(12.dp), // Bordes redondeados de 12.dp
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp) // Tasa de toque accesible >= 48.dp
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Icono Cerrar Sesión",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Cerrar Sesión",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/**
 * Fila auxiliar reutilizable para ítems de configuración con icono, etiqueta y valor.
 */
@Composable
private fun ProfileDetailRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(40.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icono $label",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
