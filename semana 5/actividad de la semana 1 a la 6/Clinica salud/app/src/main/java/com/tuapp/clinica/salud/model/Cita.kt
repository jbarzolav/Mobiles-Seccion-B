package com.tuapp.clinica.salud.model

enum class EstadoCita(val etiqueta: String) {
    CONFIRMADA("Confirmada"),
    COMPLETADA("Completada")
}

data class Cita(
    val medico: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: EstadoCita
)
