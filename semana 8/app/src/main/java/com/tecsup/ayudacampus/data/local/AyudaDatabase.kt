package com.tecsup.ayudacampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tecsup.ayudacampus.data.local.dao.CategoriaDao
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.dao.UsuarioDao
import com.tecsup.ayudacampus.data.local.dao.TicketDao

@Database(
    entities = [Categoria::class, Usuario::class, Ticket::class, Comentario::class],
    version = 1,
    exportSchema = true
)
abstract class AyudaDatabase : RoomDatabase() {
    abstract fun categoriaDao(): CategoriaDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun ticketDao(): TicketDao

    companion object {
        @Volatile private var INSTANCE: AyudaDatabase? = null

        fun obtener(context: Context): AyudaDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AyudaDatabase::class.java,
                "ayudacampus.db"
            ).build().also { INSTANCE = it }
        }
    }
}
