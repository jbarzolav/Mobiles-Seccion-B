package com.barzola.prestamodelibros

data class Prestamo(
    val tituloLibro: String,
    val tipoUsuario: String,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val fechaEntrega: String,
    val diasAtraso: Int
)
