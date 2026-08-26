package com.barzola.lab02carritokotlin

import org.junit.Test
import org.junit.Assert.*

class CarritoTest {

    @Test
    fun carritoCompleto() {
        println("===========================================")
        println("    CARRITO DE COMPRAS - TIENDA TECSUP     ")
        println("===========================================")
        println()

        val nombreCliente = "Juan Leon"
        val carrito = mutableListOf<Producto>()

        println("Cliente: $nombreCliente")
        println()

        carrito.add(Producto("Laptop HP", 2500.0, 1))
        carrito.add(Producto("Mouse Logitech", 45.5, 2))
        carrito.add(Producto("Audifonos Sony", 120.0, 1))

        for (producto in carrito) {
            println("Producto agregado: ${producto.nombre}")
        }
        println()

        val masCaro = carrito.maxByOrNull { it.precio }
        if (masCaro != null) {
            println("Producto más caro: ${masCaro.nombre} " +
                    String.format("(S/ %.2f)", masCaro.precio))
        }
        println()

        mostrarDetalle(carrito)
        println("Cantidad de productos: ${carrito.size}")
        println()

        val subtotal = calcularSubtotal(carrito)
        val igv = calcularIGV(subtotal)
        val total = calcularTotal(subtotal, igv)
        val descuento = calcularDescuento(total)
        val totalConDescuento = total - descuento

        println(String.format("Subtotal        : S/ %8.2f", subtotal))
        println(String.format("IGV (18%%)       : S/ %8.2f", igv))
        println(String.format("TOTAL           : S/ %8.2f", total))

        if (descuento > 0) {
            val porcentaje = if (total > 5000) 10 else 5
            println(String.format("Descuento (%d%%) : -S/ %7.2f", porcentaje, descuento))
            println(String.format("TOTAL CON DSCTO : S/ %8.2f", totalConDescuento))
        } else {
            println("No se aplicó descuento (total ≤ S/ 3000)")
        }

        assertEquals(2711.0, subtotal, 0.01)
        assertEquals(487.98, igv, 0.01)
        assertEquals(3198.98, total, 0.01)
        assertEquals(159.95, descuento, 0.01)
        assertEquals(3039.03, totalConDescuento, 0.01)
    }
}