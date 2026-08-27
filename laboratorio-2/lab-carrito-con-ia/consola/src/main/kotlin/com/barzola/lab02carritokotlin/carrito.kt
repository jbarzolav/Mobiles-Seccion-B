package com.barzola.lab02carritokotlin

// ============================================
// PARADIGMA POO - 4 CONCEPTOS FUNDAMENTALES
// ============================================

// ENCAPSULAMIENTO: datos y comportamiento juntos en una clase
open class Producto(val nombre: String, val precio: Double, var cantidad: Int) {
    open fun calcularSubtotal(): Double = precio * cantidad
}

// HERENCIA: ProductoDigital hereda de Producto
class ProductoDigital(nombre: String, precio: Double, cantidad: Int, val formato: String) 
    : Producto(nombre, precio, cantidad) {
    // POLIMORFISMO: sobreescribe el metodo con comportamiento diferente
    override fun calcularSubtotal(): Double = precio * cantidad * 0.9
}

// ABSTRACCION: interfaz que define un contrato
interface Calculable {
    fun calcular(): Double
}

fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.calcularSubtotal()
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double = subtotal * 0.18

fun calcularTotal(subtotal: Double, igv: Double): Double = subtotal + igv

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.calcularSubtotal()
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
    println("---------------------------------------")
}

fun calcularDescuento(total: Double): Double = when {
    total > 5000 -> total * 0.10
    total > 3000 -> total * 0.05
    else -> 0.0
}

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}

fun eliminarProducto(productos: MutableList<Producto>, nombre: String): Boolean {
    return productos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
}

fun main() {
    println("=========================================")
    println("   CARRITO DE COMPRAS - TIENDA TECSUP   ")
    println("=========================================")

    print("Ingrese su nombre: ")
    val nombreCliente = readln()
    val carrito = mutableListOf<Producto>()
    println("Cliente: $nombreCliente")
    println()

    var agregarMas = true
    while (agregarMas) {
        println("--- AGREGAR PRODUCTO ---")
        print("Nombre del producto: ")
        val nombre = readln()
        print("Precio: S/ ")
        val precio = readln().toDoubleOrNull() ?: 0.0
        print("Cantidad: ")
        val cantidad = readln().toIntOrNull() ?: 1

        carrito.add(Producto(nombre, precio, cantidad))
        println("Producto '$nombre' agregado al carrito.")
        println()

        print("Desea agregar otro producto? (s/n): ")
        val respuesta = readln()
        agregarMas = respuesta.lowercase() == "s"
        println()
    }

    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")
    println()

    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " + String.format("(S/ %.2f)", masCaro.precio))
    }
    println()

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

    println()
    println("========== BUSCAR PRODUCTO ==========")
    print("Nombre del producto a buscar: ")
    val nombreBuscar = readln()
    val encontrado = buscarProducto(carrito, nombreBuscar)
    if (encontrado != null) {
        println("Producto encontrado: ${encontrado.nombre} - S/ ${encontrado.precio}")
    } else {
        println("Producto '$nombreBuscar' no encontrado")
    }

    println()
    println("========== ELIMINAR PRODUCTO ==========")
    print("Nombre del producto a eliminar: ")
    val nombreEliminar = readln()
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