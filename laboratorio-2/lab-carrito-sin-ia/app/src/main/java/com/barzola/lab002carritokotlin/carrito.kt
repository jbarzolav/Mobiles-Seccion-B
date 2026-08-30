package com.barzola.lab02carritokotlin

data class Producto(val nombre: String, val precio: Double, var cantidad: Int)

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double {
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}

fun eliminarProducto(productos: MutableList<Producto>, nombre: String): Boolean {
    return productos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
}

fun ejecutarCarrito(): String {
    val sb = StringBuilder()
    sb.appendLine("=========================================")
    sb.appendLine("   CARRITO DE COMPRAS - TIENDA TECSUP   ")
    sb.appendLine("=========================================")
    sb.appendLine()

    val nombreCliente = "Juan Leon"
    val carrito = mutableListOf<Producto>()
    sb.appendLine("Cliente: $nombreCliente")
    sb.appendLine()

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Audifonos Sony", 120.0, 1))
    carrito.add(Producto("Curso Kotlin", 150.0, 1))

    sb.append(mostrarDetalle(carrito))
    sb.appendLine("Cantidad de productos: ${carrito.size}")
    sb.appendLine()

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        sb.appendLine("Producto mas caro: ${masCaro.nombre} " + String.format("(S/ %.2f)", masCaro.precio))
    }
    sb.appendLine()

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val totalBase = calcularTotal(subtotal, igv)
    val descuento = calcularDescuento(totalBase)
    val totalConDescuento = totalBase - descuento

    sb.appendLine(String.format("%-25s S/ %8.2f", "Subtotal :", subtotal))
    sb.appendLine(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
    sb.appendLine(String.format("%-25s S/ %8.2f", "TOTAL :", totalBase))
    sb.appendLine(String.format("%-25s S/ %8.2f", "Descuento aplicado :", descuento))
    sb.appendLine(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", totalConDescuento))

    sb.appendLine()
    sb.appendLine("========== BUSCAR PRODUCTO ==========")
    val nombreBuscar = "Mouse Logitech"
    val encontrado = buscarProducto(carrito, nombreBuscar)
    if (encontrado != null) {
        sb.appendLine("Producto encontrado: ${encontrado.nombre} - S/ ${encontrado.precio}")
    } else {
        sb.appendLine("Producto '$nombreBuscar' no encontrado")
    }

    sb.appendLine()
    sb.appendLine("========== ELIMINAR PRODUCTO ==========")
    val nombreEliminar = "Audifonos Sony"
    val eliminado = eliminarProducto(carrito, nombreEliminar)
    if (eliminado) {
        sb.appendLine("Producto '$nombreEliminar' eliminado del carrito")
    } else {
        sb.appendLine("Producto '$nombreEliminar' no encontrado para eliminar")
    }
    sb.appendLine()

    sb.append(mostrarDetalle(carrito))
    sb.appendLine("Cantidad de productos: ${carrito.size}")
    sb.appendLine()

    val nuevoSubtotal = calcularSubtotal(carrito)
    val nuevoIgv = calcularIGV(nuevoSubtotal)
    val nuevoTotal = calcularTotal(nuevoSubtotal, nuevoIgv)
    val nuevoDescuento = calcularDescuento(nuevoTotal)
    val nuevoTotalConDescuento = nuevoTotal - nuevoDescuento

    sb.appendLine(String.format("%-25s S/ %8.2f", "Subtotal :", nuevoSubtotal))
    sb.appendLine(String.format("%-25s S/ %8.2f", "IGV (18%):", nuevoIgv))
    sb.appendLine(String.format("%-25s S/ %8.2f", "TOTAL :", nuevoTotal))
    sb.appendLine(String.format("%-25s S/ %8.2f", "Descuento aplicado :", nuevoDescuento))
    sb.appendLine(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", nuevoTotalConDescuento))

    return sb.toString()
}

fun main() {
    println(ejecutarCarrito())
}