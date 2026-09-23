package com.tuapp.clinica.tecsupfit.ui.screens.rutinas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tuapp.clinica.tecsupfit.model.SampleData
import com.tuapp.clinica.tecsupfit.ui.components.AppBottomBar
import com.tuapp.clinica.tecsupfit.ui.components.RutinaCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RutinasScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Rutinas") })
        },
        bottomBar = {
            AppBottomBar(navController = navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(SampleData.rutinas) { rutina ->
                RutinaCard(rutina = rutina)
            }
        }
    }
}
