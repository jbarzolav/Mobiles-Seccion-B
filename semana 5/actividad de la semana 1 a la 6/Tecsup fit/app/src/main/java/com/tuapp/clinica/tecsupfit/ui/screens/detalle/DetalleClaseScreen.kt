package com.tuapp.clinica.tecsupfit.ui.screens.detalle

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.tuapp.clinica.tecsupfit.model.SampleData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleClaseScreen(
    navController: NavHostController,
    claseId: Int
) {
    val clase = SampleData.clases.find { it.id == claseId }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle de clase") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = clase?.nombre ?: "Clase no encontrada",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
