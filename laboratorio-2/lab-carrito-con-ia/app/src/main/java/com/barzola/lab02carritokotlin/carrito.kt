package com.barzola.lab02carritokotlin

// ABSTRACCIÓN: Interfaz que define la responsabilidad de cálculo
interface Calculable {
    fun calcularSubtotal(): Double
}

// ENCAPSULAMIENTO: Implementa Calculable
open class Producto(val nombre: String, val precio: Double, var cantidad: Int) : Calculable {
    override fun calcularSubtotal(): Double = precio * cantidad
}

// HERENCIA Y POLIMORFISMO: Hereda de Producto y aplica 10% de descuento
class ProductoDigital(nombre: String, precio: Double, cantidad: Int, val formato: String)
    : Producto(nombre, precio, cantidad) {
    override fun calcularSubtotal(): Double = precio * cantidad * 0.90
}

fun calcularSubtotal(productos: List<Producto>): Double = productos.sumOf { it.calcularSubtotal() }
fun calcularIGV(subtotal: Double): Double = subtotal * 0.18
fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

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

fun mostrarDetalle(productos: List<Producto>) {
    if (productos.isEmpty()) {
        println("\n--- El carrito está vacío ---")
        return
    }
    println("\n--------- DETALLE DEL CARRITO ---------")
    productos.forEachIndexed { i, p ->
        val tipo = if (p is ProductoDigital) " [Digital: ${p.formato}]" else ""
        println(String.format("%d. %-18s%s x%d S/ %8.2f", i + 1, p.nombre, tipo, p.cantidad, p.calcularSubtotal()))
    }
    println("---------------------------------------")
}

fun main() {
    val carrito = mutableListOf<Producto>()

    print("Ingrese el nombre del cliente: ")
    val cliente = readln().ifBlank { "Cliente Anónimo" }

    var opcion: Int
    do {
        println("\n=========================================")
        println("   CARRITO DE COMPRAS - TIENDA TECSUP ")
        println("   Cliente: $cliente")
        println("=========================================")
        println("1. Agregar producto físico")
        println("2. Agregar producto digital")
        println("3. Ver carrito y totales")
        println("4. Buscar producto por nombre")
        println("5. Eliminar producto")
        println("6. Finalizar y salir")
        print("Seleccione una opción (1-6): ")

        opcion = readln().toIntOrNull() ?: 0

        when (opcion) {
            1 -> {
                print("Nombre del producto: ")
                val nombre = readln()
                print("Precio unitario: S/ ")
                val precio = readln().toDoubleOrNull() ?: 0.0
                print("Cantidad: ")
                val cantidad = readln().toIntOrNull() ?: 1
                carrito.add(Producto(nombre, precio, cantidad))
                println("-> Producto registrado correctamente.")
            }
            2 -> {
                print("Nombre del producto digital: ")
                val nombre = readln()
                print("Precio unitario: S/ ")
                val precio = readln().toDoubleOrNull() ?: 0.0
                print("Cantidad: ")
                val cantidad = readln().toIntOrNull() ?: 1
                print("Formato (PDF/EPUB/Video): ")
                val formato = readln()
                carrito.add(ProductoDigital(nombre, precio, cantidad, formato))
                println("-> Producto digital registrado con 10% de descuento aplicado.")
            }
            3 -> {
                mostrarDetalle(carrito)
                if (carrito.isNotEmpty()) {
                    val subtotal = calcularSubtotal(carrito)
                    val igv = calcularIGV(subtotal)
                    val totalBase = calcularTotal(subtotal, igv)
                    val descuento = calcularDescuento(totalBase)
                    val totalFinal = totalBase - descuento

                    val masCaro = carrito.maxByOrNull { it.precio }
                    println("Producto más caro (unidad): ${masCaro?.nombre} (S/ ${masCaro?.precio})")
                    println(String.format("%-25s S/ %8.2f", "Subtotal :", subtotal))
                    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
                    println(String.format("%-25s S/ %8.2f", "TOTAL Base:", totalBase))
                    println(String.format("%-25s S/ %8.2f", "Descuento por monto:", descuento))
                    println(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", totalFinal))
                }
            }
            4 -> {
                print("Nombre del producto a buscar: ")
                val busqueda = readln()
                val encontrado = buscarProducto(carrito, busqueda)
                if (encontrado != null) {
                    println("-> Encontrado: ${encontrado.nombre} | S/ ${encontrado.precio} | Cantidad: ${encontrado.cantidad}")
                } else {
                    println("-> El producto '$busqueda' no existe en el carrito.")
                }
            }
            5 -> {
                print("Nombre del producto a eliminar: ")
                val eliminar = readln()
                if (eliminarProducto(carrito, eliminar)) {
                    println("-> Producto '$eliminar' eliminado del carrito.")
                } else {
                    println("-> No se encontró el producto a eliminar.")
                }
            }
            6 -> println("¡Gracias por su compra!")
            else -> println("Opción no válida. Intente de nuevo.")
        }
    } while (opcion != 6)
}