package com.tecsup.tecsupstore

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String
)

val listaProductosDemo = listOf(
    Producto(1, "Audífonos", 89.00, "Más vendidos"),
    Producto(2, "Smartwatch", 199.00, "Más vendidos"),
    Producto(3, "Funda celular", 25.00, "Accesorios"),
    Producto(4, "Cargador Carga Rápida", 45.00, "Accesorios"),
    Producto(5, "Mouse Inalámbrico", 65.00, "Cómputo"),
    Producto(6, "Teclado Mecánico", 140.00, "Cómputo")
)