package com.tecsup.ayudacampus

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.repository.AyudaRepositoryRoom
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicketRepositoryTest {
    @Test
    fun cicloDeTicketConComentariosYConteos() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val db = Room.inMemoryDatabaseBuilder(context, AyudaDatabase::class.java).build()
        try {
            val repo = AyudaRepositoryRoom(db.categoriaDao(), db.usuarioDao(), db.ticketDao())
            db.usuarioDao().insertarTodos(listOf(
                Usuario(nombre = "Alumno", correo = "alumno@campus.test", clave = "1234", rol = Rol.CLIENTE.name)
            ))
            val autor = checkNotNull(repo.login("alumno@campus.test", "1234"))
            assertNull(repo.login("alumno@campus.test", "incorrecta"))
            val categoriaId = db.categoriaDao().insertar(Categoria(nombre = "Hardware", icono = "pc")).toInt()
            repo.guardarCategoria(Categoria(nombre = "Software", icono = "code"))
            val inicio = System.currentTimeMillis()
            val id = repo.crearTicket(autor.id, categoriaId, "Equipo averiado", "El equipo no enciende", "Lab 1", Prioridad.ALTA).toInt()
            val creado = checkNotNull(repo.observarTicket(id).first())
            assertEquals(EstadoTicket.ABIERTO.name, creado.ticket.estado)
            assertEquals(Prioridad.ALTA.name, creado.ticket.prioridad)
            assertTrue(creado.ticket.fecha >= inicio)
            assertEquals(autor.id, creado.cliente.id)
            assertEquals("Hardware", creado.categoria.nombre)
            assertEquals(id, repo.observarTicketsCliente(autor.id).first().single().ticket.id)
            assertTrue(repo.observarTicketsCliente(autor.id + 1).first().isEmpty())
            assertEquals(1, repo.observarTodosLosTickets().first().size)
            assertEquals(1, repo.observarCantidadPorEstado(EstadoTicket.ABIERTO).first())
            assertEquals(mapOf("Hardware" to 1, "Software" to 0),
                repo.observarCantidadPorCategoria().first().associate { it.categoria to it.cantidad })
            assertEquals(0, repo.observarAltasSinAtender(inicio - 3_600_000).first())
            assertEquals(1, repo.observarAltasSinAtender(System.currentTimeMillis() + 1).first())

            repo.agregarComentario(id, autor, "Necesito asistencia")
            val comentario = checkNotNull(repo.observarTicket(id).first()).comentarios.single()
            assertEquals(autor.nombre, comentario.autorNombre)
            assertEquals(autor.rol, comentario.autorRol)
            assertEquals("Necesito asistencia", comentario.texto)
            repo.cambiarPrioridad(id, Prioridad.BAJA)
            repo.cambiarEstado(id, EstadoTicket.RESUELTO)
            assertEquals(Prioridad.BAJA.name, checkNotNull(repo.observarTicket(id).first()).ticket.prioridad)
            assertEquals(1, repo.observarCantidadPorEstado(EstadoTicket.RESUELTO).first())
            repo.cerrarConCalificacion(id, 4)
            val cerrado = checkNotNull(repo.observarTicket(id).first()).ticket
            assertEquals(EstadoTicket.CERRADO.name, cerrado.estado)
            assertEquals(4, cerrado.calificacion)
            assertTrue(checkNotNull(cerrado.fechaCierre) >= inicio)
            repo.reabrir(id)
            val reabierto = checkNotNull(repo.observarTicket(id).first()).ticket
            assertEquals(EstadoTicket.ABIERTO.name, reabierto.estado)
            assertNull(reabierto.fechaCierre)
            assertNull(reabierto.calificacion)
        } finally {
            db.close()
        }
    }
}
