package com.tecsup.ayudacampus.data.local

import androidx.sqlite.db.SupportSQLiteDatabase
import com.tecsup.ayudacampus.data.local.entity.Categoria

object DatosIniciales {
    // Room llama onCreate una sola vez; no se reinician los cambios al abrir la app.
    fun insertarCategorias(db: SupportSQLiteDatabase) {
        val categorias = listOf(
            Categoria(nombre = "Hardware", icono = "pc"),
            Categoria(nombre = "Software", icono = "code"),
            Categoria(nombre = "Red / Wi-Fi", icono = "wifi"),
            Categoria(nombre = "Cuenta", icono = "key"),
            Categoria(nombre = "Impresión", icono = "printer")
        )
        categorias.forEach { categoria ->
            db.execSQL("INSERT INTO categorias (nombre, icono, activa) VALUES (?, ?, ?)",
                arrayOf<Any>(categoria.nombre, categoria.icono, 1))
        }
    }
}
