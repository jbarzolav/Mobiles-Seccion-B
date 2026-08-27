package com.barzola.lab02carritokotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CarritoActivity : AppCompatActivity() {

    private lateinit var etNombreCliente: EditText
    private lateinit var etNombreProducto: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etCantidad: EditText
    private lateinit var tvDetalle: TextView
    private lateinit var tvResultados: TextView
    private val carrito = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        etNombreCliente = findViewById(R.id.etNombreCliente)
        etNombreProducto = findViewById(R.id.etNombreProducto)
        etPrecio = findViewById(R.id.etPrecio)
        etCantidad = findViewById(R.id.etCantidad)
        tvDetalle = findViewById(R.id.tvDetalle)
        tvResultados = findViewById(R.id.tvResultados)

        findViewById<Button>(R.id.btnAgregar).setOnClickListener {
            agregarProducto()
        }

        findViewById<Button>(R.id.btnCalcular).setOnClickListener {
            calcularTotales()
        }
    }

    private fun agregarProducto() {
        val nombre = etNombreProducto.text.toString()
        val precio = etPrecio.text.toString().toDoubleOrNull() ?: 0.0
        val cantidad = etCantidad.text.toString().toIntOrNull() ?: 1

        if (nombre.isNotEmpty() && precio > 0) {
            carrito.add(Producto(nombre, precio, cantidad))
            tvDetalle.text = mostrarDetalle(carrito)
            etNombreProducto.text.clear()
            etPrecio.text.clear()
            etCantidad.setText("1")
        }
    }

    private fun calcularTotales() {
        if (carrito.isEmpty()) {
            tvResultados.text = "Agregue productos primero"
            return
        }

        val nombreCliente = etNombreCliente.text.toString()
        val subtotal = calcularSubtotal(carrito)
        val igv = calcularIGV(subtotal)
        val total = calcularTotal(subtotal, igv)
        val descuento = calcularDescuento(total)
        val totalFinal = total - descuento

        val masCaro = carrito.maxByOrNull { it.precio }

        val sb = StringBuilder()
        sb.appendLine("Cliente: $nombreCliente")
        sb.appendLine()
        if (masCaro != null) {
            sb.appendLine("Producto mas caro: ${masCaro.nombre} (S/ ${String.format("%.2f", masCaro.precio)})")
        }
        sb.appendLine()
        sb.appendLine(String.format("%-25s S/ %8.2f", "Subtotal :", subtotal))
        sb.appendLine(String.format("%-25s S/ %8.2f", "IGV (18%):", igv))
        sb.appendLine(String.format("%-25s S/ %8.2f", "TOTAL :", total))
        sb.appendLine(String.format("%-25s S/ %8.2f", "Descuento aplicado :", descuento))
        sb.appendLine(String.format("%-25s S/ %8.2f", "TOTAL CON DESCUENTO:", totalFinal))

        tvResultados.text = sb.toString()
    }

    private fun mostrarDetalle(productos: List<Producto>): String {
        val sb = StringBuilder()
        sb.appendLine("--------- DETALLE DEL CARRITO ---------")
        var i = 1
        for (p in productos) {
            val importe = p.calcularSubtotal()
            sb.appendLine(String.format("%d. %-20s x%d S/ %8.2f", i, p.nombre, p.cantidad, importe))
            i++
        }
        sb.appendLine("---------------------------------------")
        sb.appendLine("Cantidad de productos: ${productos.size}")
        return sb.toString()
    }
}