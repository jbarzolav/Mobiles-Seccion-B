package com.tuapp.clinica.tecsupfit.model

data class Clase(
    val id: Int,
    val nombre: String,
    val hora: String,
    val sala: String,
    val duracion: String,
    val descripcion: String,
    val cuposDisponibles: Int,
    val cuposTotal: Int
)
