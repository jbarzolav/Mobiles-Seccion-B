package com.barzola.lab02carritokotlin

import org.junit.Test

class PruebaCarrito {
    @Test
    fun correrConsola() {
        // Versión con datos hardcodeados para testing
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
        carrito.add(ProductoDigital("Curso Kotlin", 150.0, 1, "PDF"))

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
        val nombreBuscar = "Mouse Logitech"
        val encontrado = buscarProducto(carrito, nombreBuscar)
        if (encontrado != null) {
            println("Producto encontrado: ${encontrado.nombre} - S/ ${encontrado.precio}")
        } else {
            println("Producto '$nombreBuscar' no encontrado")
        }

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
}