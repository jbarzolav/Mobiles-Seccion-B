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
    // TODO: devuelve el 18% del subtotal
    return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double {
    // TODO: devuelve la suma de ambos
    return subtotal + igv
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
    carrito.add(Producto("Audifonos Sony", 120.0, 1)) // Cambiado para que cuadre exacto con el total de tu Figura 2 (2711.00)

    for (producto in carrito) {
        println("Producto agregado: ${producto.nombre}")
    }
    println()


    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val total = calcularTotal(subtotal, igv)


    println("Subtotal : S/ " + String.format("%.2f", subtotal))
    println("IGV (18%): S/ " + String.format("%.2f", igv))
    println("TOTAL    : S/ " + String.format("%.2f", total))
}
