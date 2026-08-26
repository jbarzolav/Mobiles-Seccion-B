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

// PARTE 4: Reporte con formato

fun mostrarDetalle(productos: List<Producto>) {
    println("---------- DETALLE DEL CARRITO ----------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
        i++
    }
    println("------------------------------------------")
}

// PARTE 5: Lógica adicional — producto más caro y descuento

fun calcularDescuento(total: Double): Double {
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

// RETO ADICIONAL: Buscar y eliminar producto

fun buscarProducto(productos: List<Producto>, nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}

fun eliminarProducto(productos: MutableList<Producto>, nombre: String): Boolean {
    return productos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
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

    // Producto más caro
    val masCaro = carrito.maxByOrNull { it.precio }
    if (masCaro != null) {
        println("Producto más caro: ${masCaro.nombre} " +
                String.format("(S/ %.2f)", masCaro.precio))
    }
    println()

    // Mostrar detalle con formato
    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")
    println()

    // Calcular y mostrar resultados
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

    // RETO ADICIONAL: Buscar producto
    println()
    println("========== BUSCAR PRODUCTO ==========")
    val nombreBuscar = "Mouse Logitech"
    val encontrado = buscarProducto(carrito, nombreBuscar)
    if (encontrado != null) {
        println("Producto encontrado: ${encontrado.nombre} - S/ ${encontrado.precio}")
    } else {
        println("Producto '$nombreBuscar' no encontrado")
    }

    // RETO ADICIONAL: Eliminar producto
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

    // Mostrar detalle y totales actualizados
    mostrarDetalle(carrito)
    println("Cantidad de productos: ${carrito.size}")
    println()

    val nuevoSubtotal = calcularSubtotal(carrito)
    val nuevoIgv = calcularIGV(nuevoSubtotal)
    val nuevoTotal = calcularTotal(nuevoSubtotal, nuevoIgv)
    val nuevoDescuento = calcularDescuento(nuevoTotal)
    val nuevoTotalConDescuento = nuevoTotal - nuevoDescuento

    println(String.format("Subtotal        : S/ %8.2f", nuevoSubtotal))
    println(String.format("IGV (18%%)       : S/ %8.2f", nuevoIgv))
    println(String.format("TOTAL           : S/ %8.2f", nuevoTotal))

    if (nuevoDescuento > 0) {
        val porcentaje = if (nuevoTotal > 5000) 10 else 5
        println(String.format("Descuento (%d%%) : -S/ %7.2f", porcentaje, nuevoDescuento))
        println(String.format("TOTAL CON DSCTO : S/ %8.2f", nuevoTotalConDescuento))
    } else {
        println("No se aplicó descuento (total ≤ S/ 3000)")
    }
}