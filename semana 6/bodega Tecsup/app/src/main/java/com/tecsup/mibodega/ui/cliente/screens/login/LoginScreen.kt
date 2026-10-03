package com.tecsup.mibodega.ui.cliente.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.BotonSecundario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme

/**
 * Pantalla 2: Inicio de sesión.
 * El TODO del esqueleto decía "pantalla de login, aún no está en el mockup",
 * así que se creó con el MISMO estilo de Registro: formulario propio con
 * remember, CampoTexto/BotonPrimario del proyecto y botón deshabilitado
 * mientras falten datos.
 *
 * No valida contra ningún servicio todavía (no hay backend); solo deja
 * pasar al Inicio cuando el teléfono no está vacío y la clave tiene
 * al menos 4 caracteres.
 *
 * @param onIniciarSesion éxito del login, navega ClienteApp
 * @param onRegistrarse ir a la pantalla de registro
 */
@Composable
fun LoginScreen(
    onIniciarSesion: () -> Unit,
    onRegistrarse: () -> Unit,
    onVolver: () -> Unit
) {
    var telefono by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    val datosListos = telefono.isNotBlank() && clave.length >= 4

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        EncabezadoLogin(onVolver = onVolver)

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Bienvenido de nuevo",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Ingresa con tu teléfono y contraseña",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(28.dp))

        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefono,
            onValorCambia = { telefono = it },
            placeholder = "987 654 321",
            teclado = KeyboardType.Phone
        )

        Spacer(Modifier.height(16.dp))

        CampoTexto(
            etiqueta = "Contraseña",
            valor = clave,
            onValorCambia = { clave = it },
            placeholder = "mínimo 4 caracteres",
            teclado = KeyboardType.Password
        )

        Spacer(Modifier.height(28.dp))

        BotonPrimario(
            texto = "Iniciar sesión",
            onClick = onIniciarSesion,
            habilitado = datosListos
        )

        Spacer(Modifier.height(16.dp))

        BotonSecundario(
            texto = "Crear cuenta",
            onClick = onRegistrarse
        )

        Spacer(Modifier.height(24.dp))
    }
}

// Sub-composables PRIVADOS: solo los usa esta pantalla.

@Composable
private fun EncabezadoLogin(onVolver: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onVolver) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
        }
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f, fill = false)
        )
        Spacer(Modifier.size(48.dp)) // balancea el ancho del ícono de la izquierda
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginPreview() {
    BodegaTheme {
        LoginScreen(
            onIniciarSesion = {},
            onRegistrarse = {},
            onVolver = {}
        )
    }
}
