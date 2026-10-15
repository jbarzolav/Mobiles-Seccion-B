package com.tecsup.ayudacampus

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.repository.AyudaRepositoryRoom
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoriaRepositoryTest {
    @Test
    fun categoriasSeGuardanActualizanFiltranYEliminan() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room.inMemoryDatabaseBuilder(context, AyudaDatabase::class.java).build()
        try {
            val dao = db.categoriaDao()
            val repo = AyudaRepositoryRoom(dao)
            repo.guardarCategoria(Categoria(nombre = "Software", icono = "code"))
            repo.guardarCategoria(Categoria(nombre = "Hardware", icono = "pc"))
            val iniciales = repo.observarCategorias().first()
            assertEquals(listOf("Hardware", "Software"), iniciales.map { it.nombre })
            assertTrue(iniciales.all { it.id > 0 })

            val hardware = iniciales.first()
            repo.guardarCategoria(hardware.copy(nombre = "Equipos"))
            assertEquals(2, dao.contar())
            assertEquals("Equipos", repo.observarCategorias().first().first().nombre)

            repo.cambiarActiva(hardware.id, false)
            assertEquals(listOf("Software"), repo.observarCategoriasActivas().first().map { it.nombre })
            assertEquals(2, repo.observarCategorias().first().size)

            repo.eliminarCategoria(hardware)
            assertEquals(1, dao.contar())
            assertEquals("Software", repo.observarCategorias().first().single().nombre)
        } finally {
            db.close()
        }
    }
}
