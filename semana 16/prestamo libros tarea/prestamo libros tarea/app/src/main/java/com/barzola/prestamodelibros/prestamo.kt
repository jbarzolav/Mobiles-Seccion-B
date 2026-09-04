package com.barzola.prestamodelibros

data class Prestamo(
    val tituloLibro: String,
    val tipoUsuario: String,
    val fechaPrestamo: String,
    val fechaDevolucion: String,
    val fechaEntrega: String,
    val diasAtraso: Int
)

fun calcularMulta(diasAtraso: Int, tipoUsuario: String): Double {
    val multaPorDia = when (tipoUsuario) {
        "Docente" -> 2.00
        else -> 1.50
    }
    return diasAtraso * multaPorDia
}

fun mostrarTablaMultas(diasAtraso: Int, tipoUsuario: String): String {
    val sb = StringBuilder()
    val multaPorDia = when (tipoUsuario) {
        "Docente" -> 2.00
        else -> 1.50
    }

    sb.appendLine()
    sb.appendLine(String.format("%-5s %-12s %-12s %-12s", "Dia", "Fecha", "Multa/Dia", "Acumulado"))
    sb.appendLine("------------------------------------------------")

    for (i in 1..diasAtraso) {
        val acumulado = i * multaPorDia
        sb.appendLine(String.format("%-5d %-12s S/ %-10.2f S/ %-10.2f", i, "Dia $i", multaPorDia, acumulado))
    }

    sb.appendLine("------------------------------------------------")
    sb.appendLine(String.format("Multa Total: S/ %.2f", diasAtraso * multaPorDia))

    return sb.toString()
}

fun ejecutarSistema() {
    println("=========================================")
    println("   SISTEMA DE MULTAS - BIBLIOTECA       ")
    println("=========================================")
    println()

    print("Titulo del libro: ")
    val tituloLibro = readLine() ?: ""

    println("Tipo de usuario:")
    println("1. Docente")
    println("2. Alumno")
    print("Seleccione: ")
    val opcion = readLine() ?: "2"
    val tipoUsuario = when (opcion) {
        "1" -> "Docente"
        else -> "Alumno"
    }

    print("Fecha de prestamo (DD/MM/AA): ")
    val fechaPrestamo = readLine() ?: ""

    print("Fecha de devolucion (DD/MM/AA): ")
    val fechaDevolucion = readLine() ?: ""

    print("Fecha de entrega real (DD/MM/AA): ")
    val fechaEntrega = readLine() ?: ""

    print("Dias de atraso: ")
    val diasAtraso = readLine()?.toIntOrNull() ?: 0

    val prestamo = Prestamo(tituloLibro, tipoUsuario, fechaPrestamo, fechaDevolucion, fechaEntrega, diasAtraso)

    println()
    println("=========================================")
    println("   RESUMEN DEL PRESTAMO                 ")
    println("=========================================")
    println("Libro: ${prestamo.tituloLibro}")
    println("Usuario: ${prestamo.tipoUsuario}")
    println("Fecha prestamo: ${prestamo.fechaPrestamo}")
    println("Fecha devolucion: ${prestamo.fechaDevolucion}")
    println("Fecha entrega real: ${prestamo.fechaEntrega}")
    println("Dias de atraso: ${prestamo.diasAtraso}")

    if (diasAtraso > 0) {
        println("Estado: DEVUELTO CON $diasAtraso DIA(S) DE ATRASO")
        println("Multa por dia: S/ ${when (tipoUsuario) { "Docente" -> "2.00" else -> "1.50" }} (${tipoUsuario})")
        print(mostrarTablaMultas(diasAtraso, tipoUsuario))
    } else {
        println("Estado: DEVUELTO A TIEMPO")
        println("Multa Total: S/ 0.00")
    }
}

fun main() {
    ejecutarSistema()
}
