package com.barzola.lab02carritokotlin

// ============================================
// PARADIGMA POO - 4 CONCEPTOS FUNDAMENTALES
// ============================================

// ENCAPSULAMIENTO: datos y comportamiento juntos en una clase
// La clase Producto encapsula nombre, precio, cantidad y el método calcularSubtotal
open class Producto(val nombre: String, val precio: Double, var cantidad: Int) {
    // Método que pertenece a la clase (encapsulado)
    open fun calcularSubtotal(): Double = precio * cantidad
}

// HERENCIA: ProductoDigital hereda de Producto
// Reutiliza las propiedades y métodos de Producto
class ProductoDigital(nombre: String, precio: Double, cantidad: Int, val formato: String) 
    : Producto(nombre, precio, cantidad) {
    
    // POLIMORFISMO: sobreescribe el método con comportamiento diferente
    // Un producto digital tiene 10% de descuento
    override fun calcularSubtotal(): Double = precio * cantidad * 0.9
}

// ABSTRACCIÓN: interfaz que define un contrato sin implementación
interface Calculable {
    fun calcular(): Double
}

// Funciones de cálculo
fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.calcularSubtotal()  // POLIMORFISMO: se ejecuta según el tipo
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
        val importe = p.calcularSubtotal()  // POLIMORFISMO
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

fun main() {
    println("=========================================")
    println("	CARRITO DE COMPRAS - TIENDA TECSUP	")
    println("=========================================")

    // PEDIR NOMBRE DEL CLIENTE
    print("Ingrese su nombre: ")
    val nombreCliente = readLine() ?: ""
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente")
    println()

    // PEDIR PRODUCTOS AL USUARIO
    var agregarMas = true
    while (agregarMas) {
        println("--- AGREGAR PRODUCTO ---")
        print("Nombre del producto: ")
        val nombre = readLine() ?: ""
        print("Precio: S/ ")
        val precio = readLine()?.toDoubleOrNull() ?: 0.0
        print("Cantidad: ")
        val cantidad = readLine()?.toIntOrNull() ?: 1

        carrito.add(Producto(nombre, precio, cantidad))
        println("Producto '$nombre' agregado al carrito.")
        println()

        print("¿Desea agregar otro producto? (s/n): ")
        val respuesta = readLine() ?: "n"
        agregarMas = respuesta.lowercase() == "s"
        println()
    }

    // MOSTRAR DETALLE
    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")
    println()

    // PRODUCTO MÁS CARO
    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " + String.format("(S/ %.2f)", masCaro.precio))
    }
    println()

    // CALCULAR TOTALES
    val subtotal = calcularSubtotal(carrito)
    val igv = calcularIGV(subtotal)
    val totalBase = calcularTotal(subtotal, igv)
    val descuento = calcularDescuento(totalBase)
    val totalConDescuento = totalBase - descuento

    println(String.format("%-25s S/ %8.2f", "Subtotal :", subtotal))
    println(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
    println(String.format("%-25s S/ %8.2f", "TOTAL :", totalBase))
    println(String.format("%-25s S/ %8.2f", "Descuento aplicado :", descuento))
    println(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", totalConDescuento))

    // BUSCAR PRODUCTO
    println()
    println("========== BUSCAR PRODUCTO ==========")
    print("Nombre del producto a buscar: ")
    val nombreBuscar = readLine() ?: ""
    val encontrado = buscarProducto(carrito, nombreBuscar)
    if (encontrado != null) {
        println("Producto encontrado: ${encontrado.nombre} - S/ ${encontrado.precio}")
    } else {
        println("Producto '$nombreBuscar' no encontrado")
    }

    // ELIMINAR PRODUCTO
    println()
    println("========== ELIMINAR PRODUCTO ==========")
    print("Nombre del producto a eliminar: ")
    val nombreEliminar = readLine() ?: ""
    val eliminado = eliminarProducto(carrito, nombreEliminar)
    if (eliminado) {
        println("Producto '$nombreEliminar' eliminado del carrito")
    } else {
        println("Producto '$nombreEliminar' no encontrado para eliminar")
    }
    println()

    // MOSTRAR TOTALES ACTUALIZADOS
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