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

fun mostrarTablaMultas(diasAtraso: Int): String {
    val sb = StringBuilder()
    val MULTA_POR_DIA = 1.50

    sb.appendLine()
    sb.appendLine(String.format("%-5s %-12s %-12s %-12s", "Dia", "Fecha", "Multa/Dia", "Acumulado"))
    sb.appendLine("------------------------------------------------")

    for (i in 1..diasAtraso) {
        val acumulado = i * MULTA_POR_DIA
        sb.appendLine(String.format("%-5d %-12s S/ %-10.2f S/ %-10.2f", i, "Dia $i", MULTA_POR_DIA, acumulado))
    }

    sb.appendLine("------------------------------------------------")
    sb.appendLine(String.format("Multa Total: S/ %.2f", diasAtraso * MULTA_POR_DIA))

    return sb.toString()
}
