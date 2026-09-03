package com.barzola.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RegistroNotas()
                }
            }
        }
    }
}

@Composable
fun RegistroNotas() {
    var nota1 by remember { mutableFloatStateOf(0f) }
    var nota2 by remember { mutableFloatStateOf(0f) }
    var nota3 by remember { mutableFloatStateOf(0f) }
    var nota4 by remember { mutableFloatStateOf(0f) }
    var redondear by remember { mutableStateOf(false) }
    var confirmado by remember { mutableStateOf(false) }
    var calculado by remember { mutableStateOf(false) }
    var promedioPonderado by remember { mutableFloatStateOf(0f) }
    var promedioFinal by remember { mutableFloatStateOf(0f) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6200EE))
                .padding(16.dp)
        ) {
            Text(
                text = "Registro de Notas",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF3E5F5),
                            Color.White
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Notas del ciclo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Desliza para asignar cada nota (0 a 20)",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                CursoSlider("Fundamentos de Programación", "20%", nota1) { nota1 = it }
                Spacer(modifier = Modifier.height(12.dp))
                CursoSlider("Programación Orientada a Objetos", "25%", nota2) { nota2 = it }
                Spacer(modifier = Modifier.height(12.dp))
                CursoSlider("Programación en Móviles", "30%", nota3) { nota3 = it }
                Spacer(modifier = Modifier.height(12.dp))
                CursoSlider("Base de Datos", "25%", nota4) { nota4 = it }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Redondear promedio final", fontSize = 14.sp)
                    Switch(
                        checked = redondear,
                        onCheckedChange = { redondear = it }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = confirmado,
                        onCheckedChange = { confirmado = it }
                    )
                    Text(text = "Confirmo que las notas son correctas", fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        promedioPonderado = (nota1 * 0.20f) + (nota2 * 0.25f) + (nota3 * 0.30f) + (nota4 * 0.25f)
                        promedioFinal = if (redondear) promedioPonderado.roundToInt().toFloat() else promedioPonderado
                        calculado = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = confirmado
                ) {
                    Text("CALCULAR PROMEDIO")
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = {
                        nota1 = 0f
                        nota2 = 0f
                        nota3 = 0f
                        nota4 = 0f
                        redondear = false
                        confirmado = false
                        calculado = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("LIMPIAR")
                }

                if (!calculado) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Asigna las notas y confirma para calcular",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                if (calculado) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ResultadoCard(promedioPonderado, promedioFinal, redondear, nota1, nota2, nota3, nota4)
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Desarrollado por: José Barzola Veliz",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ResultadoCard(promedioPonderado: Float, promedioFinal: Float, redondear: Boolean, nota1: Float, nota2: Float, nota3: Float, nota4: Float) {
    val (observacion, colorChip) = when {
        promedioFinal >= 17f -> "EXCELENTE" to Color(0xFF2E7D32)
        promedioFinal >= 13f -> "APROBADO" to Color(0xFF4CAF50)
        promedioFinal >= 10f -> "EN RECUPERACIÓN" to Color(0xFFFF9800)
        else -> "DESAPROBADO" to Color(0xFFF44336)
    }
    val notaTexto = if (redondear) "${promedioFinal.roundToInt()} (redondeado)" else String.format("%.2f", promedioPonderado)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Aporte por curso:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Fundamentos: ${nota1.toInt()} × 20% = ${String.format("%.2f", nota1 * 0.20f)}", fontSize = 12.sp)
            Text(text = "POO: ${nota2.toInt()} × 25% = ${String.format("%.2f", nota2 * 0.25f)}", fontSize = 12.sp)
            Text(text = "Móviles: ${nota3.toInt()} × 30% = ${String.format("%.2f", nota3 * 0.30f)}", fontSize = 12.sp)
            Text(text = "Base de Datos: ${nota4.toInt()} × 25% = ${String.format("%.2f", nota4 * 0.25f)}", fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Promedio ponderado: ${String.format("%.2f", promedioPonderado)}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Promedio final: $notaTexto",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = MaterialTheme.shapes.small,
                color = colorChip
            ) {
                Text(
                    text = observacion,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when {
                    promedioFinal >= 17f -> "¡Excelente! Has obtenido la mejor calificación."
                    promedioFinal >= 13f -> "¡Felicidades! Has aprobado el ciclo."
                    promedioFinal >= 10f -> "Estás en recuperación. Esfuerzate más."
                    else -> "Lo siento. No alcanzaste la nota mínima."
                },
                fontSize = 14.sp,
                color = colorChip
            )
        }
    }
}

@Composable
fun CursoSlider(nombre: String, peso: String, value: Float, onValueChange: (Float) -> Unit) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$nombre ($peso)", fontSize = 14.sp)
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (value < 13f) Color(0xFFF44336) else Color(0xFF4CAF50)
            ) {
                Text(
                    text = "${value.toInt()}",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..20f,
            steps = 19,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
