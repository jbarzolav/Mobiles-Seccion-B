package com.tuapp.clinica.tecsupfit.model

enum class EstadoReserva(val etiqueta: String) {
    CONFIRMADA("Confirmada"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada")
}

data class Reserva(
    val clase: String,
    val fecha: String,
    val hora: String,
    val sala: String,
    val estado: EstadoReserva
)
