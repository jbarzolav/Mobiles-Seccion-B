package com.tecsup.ayudacampus

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.DatosIniciales
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.data.repository.AyudaRepositoryRoom

class AyudaApp : Application() {
    private val database: AyudaDatabase by lazy {
        Room.databaseBuilder(this, AyudaDatabase::class.java, "ayudacampus.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    DatosIniciales.insertarCategorias(db)
                }
            })
            .build()
    }

    val repository: AyudaRepository by lazy { AyudaRepositoryRoom(database.categoriaDao()) }
}
