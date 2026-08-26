package com.barzola.lab02carritokotlin

data class Producto(
    val nombre: String,
    val precio: Double,
    var cantidad: Int
)

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
    carrito.add(Producto("Producto 3", 10.0, 1))
    carrito.add(Producto("Producto 4", 20.0, 1))

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
}
