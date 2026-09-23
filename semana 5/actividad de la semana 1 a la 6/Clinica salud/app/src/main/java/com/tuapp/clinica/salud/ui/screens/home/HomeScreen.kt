package com.tuapp.clinica.salud.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tuapp.clinica.salud.model.SampleData
import com.tuapp.clinica.salud.ui.components.EspecialidadChips

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    onAbrirDrawer: () -> Unit
) {
    var especialidadSeleccionada by remember { mutableStateOf(SampleData.especialidades.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clínica Salud+") },
                navigationIcon = {
                    IconButton(onClick = onAbrirDrawer) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hola, José",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            EspecialidadChips(
                especialidades = SampleData.especialidades,
                seleccionada = especialidadSeleccionada,
                onSeleccionar = { especialidadSeleccionada = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            // LazyColumn de medicos filtrados en el commit 6
            Text(
                text = "Especialidad: $especialidadSeleccionada",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
