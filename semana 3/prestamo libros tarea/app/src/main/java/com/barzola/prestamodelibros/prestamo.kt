package com.barzola.prestamodelibros

data class Prestamo(
    val tituloLibro: String,
    val tipoUsuario: String,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val fechaEntrega: String,
    val diasAtraso: Int
)

fun calcularMulta(diasAtraso: Int): Double {
    val MULTA_POR_DIA = 1.50
    return diasAtraso * MULTA_POR_DIA
}
