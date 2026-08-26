package com.barzola.lab02carritokotlin

import kotlin.system.exitProcess

// ============================================
// PARTE 1: Modelo de datos y menú principal
// ============================================

// Data class para representar un producto en el carrito
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    var cantidad: Int = 1
)

// Lista mutable para almacenar los productos del carrito
val carrito = mutableListOf<Producto>()

// Variables globales para el siguiente ID disponible
var siguienteId = 1

// Función para mostrar el menú principal
fun mostrarMenu() {
    println("\n===== CARRITO DE COMPRAS =====")
    println("1. Agregar producto")
    println("2. Listar productos")
    println("3. Calcular subtotal")
    println("4. Calcular IGV")
    println("5. Calcular total")
    println("6. Producto más caro")
    println("7. Aplicar descuento por monto")
    println("8. Reporte de detalle")
    println("9. Salir")
    println("==============================")
    print("Seleccione una opción: ")
}

// Función para agregar un producto al carrito
fun agregarProducto() {
    print("Nombre del producto: ")
    val nombre = readLine() ?: ""
    print("Precio: ")
    val precio = readLine()?.toDoubleOrNull() ?: 0.0
    print("Cantidad: ")
    val cantidad = readLine()?.toIntOrNull() ?: 1

    val producto = Producto(
        id = siguienteId++,
        nombre = nombre,
        precio = precio,
        cantidad = cantidad
    )
    carrito.add(producto)
    println("Producto '$nombre' agregado al carrito.")
}

// Función para listar todos los productos
fun listarProductos() {
    if (carrito.isEmpty()) {
        println("El carrito está vacío.")
    } else {
        println("\n--- Productos en el carrito ---")
        carrito.forEach { producto ->
            println("${producto.id}. ${producto.nombre} - S/ ${String.format("%.2f", producto.precio)} x ${producto.cantidad}")
        }
    }
}

// Función para calcular el subtotal
fun calcularSubtotal(): Double {
    return carrito.sumOf { it.precio * it.cantidad }
}

// Función para calcular el IGV (18%)
fun calcularIGV(): Double {
    return calcularSubtotal() * 0.18
}

// Función para calcular el total
fun calcularTotal(): Double {
    return calcularSubtotal() + calcularIGV()
}

// Función para encontrar el producto más caro
fun productoMasCaro(): Producto? {
    return carrito.maxByOrNull { it.precio }
}

// Función para aplicar descuento por monto
fun aplicarDescuento(porcentaje: Double): Double {
    return calcularTotal() * (1 - porcentaje / 100)
}

// Función para generar el reporte de detalle
fun reporteDetalle() {
    if (carrito.isEmpty()) {
        println("El carrito está vacío.")
        return
    }

    println("\n===== REPORTE DE DETALLE =====")
    println("ID  | Producto          | Precio   | Cant | Subtotal")
    println("----|-------------------|----------|------|----------")
    
    carrito.forEach { producto ->
        val subtotal = producto.precio * producto.cantidad
        println(String.format("%-3d | %-17s | S/ %6.2f | %4d | S/ %7.2f",
            producto.id, producto.nombre, producto.precio, producto.cantidad, subtotal))
    }
    
    println("----------------------------")
    println(String.format("Subtotal: S/ %.2f", calcularSubtotal()))
    println(String.format("IGV (18%%): S/ %.2f", calcularIGV()))
    println(String.format("TOTAL:    S/ %.2f", calcularTotal()))
}

// Función principal
fun main() {
    do {
        mostrarMenu()
        val opcion = readLine()?.toIntOrNull() ?: 0

        when (opcion) {
            1 -> agregarProducto()
            2 -> listarProductos()
            3 -> println("Subtotal: S/ ${String.format("%.2f", calcularSubtotal())}")
            4 -> println("IGV (18%): S/ ${String.format("%.2f", calcularIGV())}")
            5 -> println("Total: S/ ${String.format("%.2f", calcularTotal())}")
            6 -> {
                val masCaro = productoMasCaro()
                if (masCaro != null) {
                    println("Producto más caro: ${masCaro.nombre} - S/ ${String.format("%.2f", masCaro.precio)}")
                } else {
                    println("No hay productos en el carrito.")
                }
            }
            7 -> {
                println("¿Qué porcentaje de descuento desea aplicar?")
                val porcentaje = readLine()?.toDoubleOrNull() ?: 0.0
                val totalConDescuento = aplicarDescuento(porcentaje)
                println("Total con ${String.format("%.1f", porcentaje)}% de descuento: S/ ${String.format("%.2f", totalConDescuento)}")
            }
            8 -> reporteDetalle()
            9 -> {
                println("Saliendo del carrito de compras...")
                exitProcess(0)
            }
            else -> println("Opción no válida. Intente de nuevo.")
        }
    } while (true)
}