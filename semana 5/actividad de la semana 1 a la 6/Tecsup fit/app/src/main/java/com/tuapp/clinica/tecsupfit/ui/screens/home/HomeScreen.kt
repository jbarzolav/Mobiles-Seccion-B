package com.tuapp.clinica.tecsupfit.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.tuapp.clinica.tecsupfit.model.SampleData
import com.tuapp.clinica.tecsupfit.navigation.Routes
import com.tuapp.clinica.tecsupfit.ui.components.AppBottomBar
import com.tuapp.clinica.tecsupfit.ui.components.ClaseCard
import com.tuapp.clinica.tecsupfit.ui.components.FiltroChips

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    var filtroSeleccionado by remember { mutableStateOf(SampleData.filtros.first()) }

    val clasesFiltradas = remember(filtroSeleccionado) {
        if (filtroSeleccionado == "Hoy") {
            SampleData.clases.filter { it.hora.contains("am") || it.hora.contains("6:00") }
        } else {
            SampleData.clases
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TECSUP Fit") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            AppBottomBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Hola, Diego",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            FiltroChips(
                filtros = SampleData.filtros,
                seleccionada = filtroSeleccionado,
                onSeleccionar = { filtroSeleccionado = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Clases disponibles",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                items(clasesFiltradas) { clase ->
                    ClaseCard(
                        clase = clase,
                        onClick = {
                            navController.navigate(Routes.DetalleClase.create(clase.id))
                        }
                    )
                }
            }
        }
    }
}
