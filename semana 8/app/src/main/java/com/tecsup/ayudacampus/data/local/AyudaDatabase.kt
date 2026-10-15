package com.tecsup.ayudacampus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tecsup.ayudacampus.data.local.dao.CategoriaDao
import com.tecsup.ayudacampus.data.local.entity.Categoria

// Base provisional del laboratorio: únicamente el ejemplo resuelto de categorías.
@Database(entities = [Categoria::class], version = 1, exportSchema = true)
abstract class AyudaDatabase : RoomDatabase() {
    abstract fun categoriaDao(): CategoriaDao
}
