package com.barzola.lab02carritokotlin

// PARTE 2: Modelo de datos y variables

// Data class para representar un producto en el carrito
data class Producto(val nombre: String, val precio: Double, var cantidad: Int)

// PARTE 3: Funciones de cálculo

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

fun main() {
    println("===========================================")
    println("    CARRITO DE COMPRAS - TIENDA TECSUP     ")
    println("===========================================")
    println()

    // Variables del cliente y lista del carrito
    val nombreCliente = "Juan Leon"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente")
    println()

    // Agregar 3 productos al carrito
    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Audifonos Sony", 120.0, 1))

    // Listar productos agregados
    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
    println()

    // Calcular y mostrar resultados
    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)

    println(String.format("Subtotal : S/ %.2f", subtotal))
    println(String.format("IGV (18%%): S/ %.2f", igv))
    println(String.format("TOTAL    : S/ %.2f", total))
}