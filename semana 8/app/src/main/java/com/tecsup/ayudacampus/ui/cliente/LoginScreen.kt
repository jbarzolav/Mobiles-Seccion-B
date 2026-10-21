package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

@Composable
fun LoginScreen(vm: SesionViewModel) {
    var rol by rememberSaveable { mutableStateOf(Rol.CLIENTE) }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    val error by vm.error.collectAsStateWithLifecycle()
    val cargando by vm.cargando.collectAsStateWithLifecycle()
    val oscuro by vm.modoOscuro.collectAsStateWithLifecycle()
    AyudaTheme(rol = rol, darkTheme = oscuro) {
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.safeDrawingPadding().imePadding().verticalScroll(rememberScrollState()).padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Spacer(Modifier.height(24.dp))
                Text("Bienvenido", style = MaterialTheme.typography.headlineLarge)
                Text("Ingresa para continuar", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Rol.entries.forEach { opcion ->
                        FilterChip(selected = rol == opcion, onClick = { rol = opcion }, enabled = !cargando,
                            label = { Text(if (opcion == Rol.CLIENTE) "Estudiante" else "Soporte TI") },
                            modifier = Modifier.weight(1f))
                    }
                }
                AyudaTextField(correo, { correo = it }, "Correo", Modifier.fillMaxWidth(),
                    enabled = !cargando, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) })
                AyudaTextField(clave, { clave = it }, "Contraseña", Modifier.fillMaxWidth(),
                    enabled = !cargando, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) })
                error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                AyudaButton("Ingresar", { vm.login(correo, clave, rol) }, Modifier.fillMaxWidth(), cargando = cargando)
                if (rol == Rol.ADMIN) {
                    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = MaterialTheme.shapes.small) {
                        Column(Modifier.fillMaxWidth().padding(16.dp)) {
                            Text("Modo práctica", style = MaterialTheme.typography.titleSmall)
                            Text("Puedes cambiar de perfil desde Ajustes para probar ambos roles.",
                                style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
