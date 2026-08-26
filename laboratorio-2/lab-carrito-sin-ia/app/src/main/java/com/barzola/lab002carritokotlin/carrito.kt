package com.barzola.lab02carritokotlin

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

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

// Parte 5 - Paso 2: Función descuento completada de forma simple literal
fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

// Reto adicional: Buscar producto
fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}

// Reto adicional: Eliminar producto
fun eliminarProducto(productos: MutableList<Producto>, nombre: String): Boolean {
    return productos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
}

fun main() {
    println("=========================================")
    println("	CARRITO DE COMPRAS - TIENDA TECSUP	")
    println("=========================================")

    val nombreCliente = "Juan Leon"
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente")
    println()

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Audifonos Sony", 120.0, 1))

    // Paso 3: Agregamos un producto extra pesado para que el total supere los S/ 3000 y se active el descuento
    carrito.add(Producto("Monitor Gamer", 800.0, 1))

    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")
    println()

    // Parte 5 - Paso 1: Buscar e imprimir el producto más caro
    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " + String.format("(S/ %.2f)", masCaro.precio))
    }
    println()

    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val totalBase = calcularTotal(subtotal, igv)

    // Parte 5 - Paso 3: Calcular el descuento e imprimir los totales finales formatted
    val descuento = calcularDescuento(totalBase)
    val totalConDescuento = totalBase - descuento

    println(String.format("%-25s S/ %8.2f", "Subtotal :", subtotal))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
    println(String.format("%-25s S/ %8.2f", "TOTAL :", totalBase))
    println(String.format("%-25s S/ %8.2f", "Descuento aplicado :", descuento))
    println(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", totalConDescuento))

    // Reto adicional: Buscar producto
    println()
    println("========== BUSCAR PRODUCTO ==========")
    val nombreBuscar = "Mouse Logitech"
    val encontrado = buscarProducto(carrito, nombreBuscar)
    if (encontrado != null) {
        println("Producto encontrado: ${encontrado.nombre} - S/ ${encontrado.precio}")
    } else {
        println("Producto '$nombreBuscar' no encontrado")
    }

    // Reto adicional: Eliminar producto
    println()
    println("========== ELIMINAR PRODUCTO ==========")
    val nombreEliminar = "Audifonos Sony"
    val eliminado = eliminarProducto(carrito, nombreEliminar)
    if (eliminado) {
        println("Producto '$nombreEliminar' eliminado del carrito")
    } else {
        println("Producto '$nombreEliminar' no encontrado para eliminar")
    }
    println()

    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")
    println()

    val nuevoSubtotal = calcularSubtotal(carrito)
    val nuevoIgv = calcularIGV(nuevoSubtotal)
    val nuevoTotal = calcularTotal(nuevoSubtotal, nuevoIgv)
    val nuevoDescuento = calcularDescuento(nuevoTotal)
    val nuevoTotalConDescuento = nuevoTotal - nuevoDescuento

    println(String.format("%-25s S/ %8.2f", "Subtotal :", nuevoSubtotal))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", nuevoIgv))
    println(String.format("%-25s S/ %8.2f", "TOTAL :", nuevoTotal))
    println(String.format("%-25s S/ %8.2f", "Descuento aplicado :", nuevoDescuento))
    println(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", nuevoTotalConDescuento))
}
