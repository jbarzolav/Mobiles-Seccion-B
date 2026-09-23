package com.tuapp.clinica.tecsupfit.ui.screens.perfil

import androidx.compose.foundation.layout.Box
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
import com.tuapp.clinica.tecsupfit.ui.components.AppBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi perfil") })
        },
        bottomBar = {
            AppBottomBar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Mi perfil",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
