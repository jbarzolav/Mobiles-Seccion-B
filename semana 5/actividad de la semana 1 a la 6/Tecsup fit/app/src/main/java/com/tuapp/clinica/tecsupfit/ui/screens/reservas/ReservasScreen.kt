package com.tuapp.clinica.tecsupfit.ui.screens.reservas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tuapp.clinica.tecsupfit.model.SampleData
import com.tuapp.clinica.tecsupfit.ui.components.AppBottomBar
import com.tuapp.clinica.tecsupfit.ui.components.ReservaCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservasScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis reservas") })
        },
        bottomBar = {
            AppBottomBar(navController = navController)
        }
    ) { innerPadding ->
        if (SampleData.reservas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tienes reservas",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            return@Scaffold
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(SampleData.reservas) { reserva ->
                ReservaCard(reserva = reserva)
            }
        }
    }
}
