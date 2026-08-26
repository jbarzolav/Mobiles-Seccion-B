package com.barzola.lab02carritokotlin

// PARTE 2: Modelo de datos y variables

// Data class para representar un producto en el carrito
data class Producto(val nombre: String, val precio: Double, var cantidad: Int)

fun main() {
    // Variables del cliente y lista del carrito
    val nombreCliente = "Juan Leon"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente")
    println()

    // Agregar 4 productos al carrito
    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
    carrito.add(Producto("Teclado Mecánico", 120.0, 1))
    carrito.add(Producto("Monitor Samsung", 850.0, 1))

    // Listar productos agregados
    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
}