package com.tuapp.clinica.salud.model

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val anosExperiencia: Int,
    val descripcion: String
)
