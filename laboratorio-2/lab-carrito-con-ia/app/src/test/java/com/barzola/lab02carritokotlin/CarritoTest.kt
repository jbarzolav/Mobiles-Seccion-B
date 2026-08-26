package com.barzola.lab02carritokotlin

import org.junit.Test
import org.junit.Assert.*

class CarritoTest {

    // ============================================
    // TEST 1: Ejecucion completa del carrito
    // ============================================
    @Test
    fun testEjecucionCompleta() {
        val resultado = ejecutarCarrito()
        assertTrue(resultado.contains("CARRITO DE COMPRAS"))
        assertTrue(resultado.contains("Juan Leon"))
        assertTrue(resultado.contains("Laptop HP"))
        assertTrue(resultado.contains("Mouse Logitech"))
        assertTrue(resultado.contains("Audifonos Sony"))
        assertTrue(resultado.contains("Curso Kotlin"))
        println(resultado)
    }

    // ============================================
    // TEST 2: Creacion de Producto
    // ============================================
    @Test
    fun testCrearProducto() {
        val p = Producto("Laptop", 2500.0, 1)
        assertEquals("Laptop", p.nombre)
        assertEquals(2500.0, p.precio, 0.01)
        assertEquals(1, p.cantidad)
    }

    // ============================================
    // TEST 3: Calcular subtotal de un producto
    // ============================================
    @Test
    fun testCalcularSubtotalProducto() {
        val p = Producto("Mouse", 50.0, 3)
        assertEquals(150.0, p.calcularSubtotal(), 0.01)
    }

    // ============================================
    // TEST 4: ProductoDigital con descuento
    // ============================================
    @Test
    fun testProductoDigitalDescuento() {
        val pd = ProductoDigital("Curso", 100.0, 1, "PDF")
        assertEquals(90.0, pd.calcularSubtotal(), 0.01) // 10% descuento
    }

    // ============================================
    // TEST 5: Calcular subtotal de lista
    // ============================================
    @Test
    fun testCalcularSubtotalLista() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("A", 100.0, 2))  // 200
        carrito.add(Producto("B", 50.0, 1))   // 50
        assertEquals(250.0, calcularSubtotal(carrito), 0.01)
    }

    // ============================================
    // TEST 6: Calcular IGV
    // ============================================
    @Test
    fun testCalcularIGV() {
        assertEquals(180.0, calcularIGV(1000.0), 0.01)
        assertEquals(0.0, calcularIGV(0.0), 0.01)
    }

    // ============================================
    // TEST 7: Calcular total
    // ============================================
    @Test
    fun testCalcularTotal() {
        assertEquals(1180.0, calcularTotal(1000.0, 180.0), 0.01)
    }

    // ============================================
    // TEST 8: Descuento 5%
    // ============================================
    @Test
    fun testDescuento5Porciento() {
        assertEquals(200.0, calcularDescuento(4000.0), 0.01) // 4000 * 0.05
    }

    // ============================================
    // TEST 9: Descuento 10%
    // ============================================
    @Test
    fun testDescuento10Porciento() {
        assertEquals(600.0, calcularDescuento(6000.0), 0.01) // 6000 * 0.10
    }

    // ============================================
    // TEST 10: Sin descuento
    // ============================================
    @Test
    fun testSinDescuento() {
        assertEquals(0.0, calcularDescuento(2000.0), 0.01)
    }

    // ============================================
    // TEST 11: Buscar producto existente
    // ============================================
    @Test
    fun testBuscarProductoExistente() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Laptop", 2500.0, 1))
        carrito.add(Producto("Mouse", 50.0, 2))
        
        val encontrado = buscarProducto(carrito, "Mouse")
        assertNotNull(encontrado)
        assertEquals("Mouse", encontrado?.nombre)
    }

    // ============================================
    // TEST 12: Buscar producto no existente
    // ============================================
    @Test
    fun testBuscarProductoNoExistente() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Laptop", 2500.0, 1))
        
        val encontrado = buscarProducto(carrito, "Teclado")
        assertNull(encontrado)
    }

    // ============================================
    // TEST 13: Buscar sin importar mayusculas
    // ============================================
    @Test
    fun testBuscarCaseInsensitive() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Laptop", 2500.0, 1))
        
        val encontrado = buscarProducto(carrito, "laptop")
        assertNotNull(encontrado)
    }

    // ============================================
    // TEST 14: Eliminar producto existente
    // ============================================
    @Test
    fun testEliminarProductoExistente() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Laptop", 2500.0, 1))
        carrito.add(Producto("Mouse", 50.0, 2))
        
        val eliminado = eliminarProducto(carrito, "Mouse")
        assertTrue(eliminado)
        assertEquals(1, carrito.size)
    }

    // ============================================
    // TEST 15: Eliminar producto no existente
    // ============================================
    @Test
    fun testEliminarProductoNoExistente() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Laptop", 2500.0, 1))
        
        val eliminado = eliminarProducto(carrito, "Teclado")
        assertFalse(eliminado)
        assertEquals(1, carrito.size)
    }

    // ============================================
    // TEST 16: Mostrar detalle
    // ============================================
    @Test
    fun testMostrarDetalle() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Laptop", 2500.0, 1))
        
        val detalle = mostrarDetalle(carrito)
        assertTrue(detalle.contains("Laptop"))
        assertTrue(detalle.contains("2500.00"))
    }

    // ============================================
    // TEST 17: Carrito vacio
    // ============================================
    @Test
    fun testCarritoVacio() {
        val carrito = mutableListOf<Producto>()
        assertEquals(0.0, calcularSubtotal(carrito), 0.01)
        assertNull(carrito.maxByOrNull { it.precio })
    }

    // ============================================
    // TEST 18: Producto mas caro
    // ============================================
    @Test
    fun testProductoMasCaro() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Mouse", 50.0, 1))
        carrito.add(Producto("Laptop", 2500.0, 1))
        carrito.add(Producto("Teclado", 100.0, 1))
        
        val masCaro = carrito.maxByOrNull { it.precio }
        assertNotNull(masCaro)
        assertEquals("Laptop", masCaro?.nombre)
    }

    // ============================================
    // TEST 19: Calcular totales completos
    // ============================================
    @Test
    fun testCalcularTotalesCompletos() {
        val carrito = mutableListOf<Producto>()
        carrito.add(Producto("Laptop", 2500.0, 1))
        carrito.add(Producto("Mouse", 50.0, 2))
        
        val subtotal = calcularSubtotal(carrito)  // 2600
        val igv = calcularIGV(subtotal)            // 468
        val total = calcularTotal(subtotal, igv)  // 3068
        val descuento = calcularDescuento(total)   // 153.4 (5%)
        val totalFinal = total - descuento         // 2914.6
        
        assertEquals(2600.0, subtotal, 0.01)
        assertEquals(468.0, igv, 0.01)
        assertEquals(3068.0, total, 0.01)
        assertEquals(153.4, descuento, 0.01)
        assertEquals(2914.6, totalFinal, 0.01)
    }

    // ============================================
    // TEST 20: Herencia y polimorfismo
    // ============================================
    @Test
    fun testHerenciaPolimorfismo() {
        val producto: Producto = ProductoDigital("Curso", 200.0, 1, "PDF")
        
        // Verificar que es ProductoDigital
        assertTrue(producto is ProductoDigital)
        
        // Verificar polimorfismo: descuento del 10%
        assertEquals(180.0, producto.calcularSubtotal(), 0.01)
    }
}