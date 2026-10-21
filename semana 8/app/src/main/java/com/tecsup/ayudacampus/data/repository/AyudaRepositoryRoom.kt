package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.dao.CategoriaDao
import com.tecsup.ayudacampus.data.local.dao.UsuarioDao
import com.tecsup.ayudacampus.data.local.dao.TicketDao
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.model.ConteoCategoria
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.Flow

class AyudaRepositoryRoom(
    private val categorias: CategoriaDao,
    private val usuarios: UsuarioDao,
    private val tickets: TicketDao
) : AyudaRepository {
    override fun observarCategorias(): Flow<List<Categoria>> = categorias.observarTodas()
    override fun observarCategoriasActivas(): Flow<List<Categoria>> = categorias.observarActivas()
    override suspend fun cambiarActiva(id: Int, activa: Boolean) = categorias.cambiarActiva(id, activa)

    override suspend fun guardarCategoria(c: Categoria) {
        if (c.id == 0) categorias.insertar(c) else categorias.actualizar(c)
    }

    override suspend fun eliminarCategoria(c: Categoria) = categorias.eliminar(c)

    override suspend fun login(correo: String, clave: String): Usuario? = usuarios.login(correo, clave)

    override suspend fun crearTicket(
        clienteId: Int, categoriaId: Int, titulo: String,
        descripcion: String, ubicacion: String, prioridad: Prioridad
    ): Long = tickets.insertarTicket(Ticket(
        clienteId = clienteId,
        categoriaId = categoriaId,
        titulo = titulo,
        descripcion = descripcion,
        ubicacion = ubicacion,
        prioridad = prioridad.name,
        estado = EstadoTicket.ABIERTO.name,
        fecha = System.currentTimeMillis()
    ))

    override fun observarTicketsCliente(clienteId: Int): Flow<List<TicketConDetalle>> =
        tickets.observarPorCliente(clienteId)
    override fun observarTodosLosTickets(): Flow<List<TicketConDetalle>> = tickets.observarTodos()
    override fun observarTicket(id: Int): Flow<TicketConDetalle?> = tickets.observarUno(id)

    override suspend fun cambiarEstado(ticketId: Int, estado: EstadoTicket) =
        tickets.actualizarEstado(ticketId, estado.name)
    override suspend fun cambiarPrioridad(ticketId: Int, prioridad: Prioridad) =
        tickets.actualizarPrioridad(ticketId, prioridad.name)
    override suspend fun cerrarConCalificacion(ticketId: Int, calificacion: Int) =
        tickets.cerrar(ticketId, calificacion, System.currentTimeMillis())
    override suspend fun reabrir(ticketId: Int) = tickets.reabrir(ticketId)

    override suspend fun agregarComentario(ticketId: Int, autor: Usuario, texto: String) =
        tickets.insertarComentario(Comentario(
            ticketId = ticketId,
            autorId = autor.id,
            autorNombre = autor.nombre,
            autorRol = autor.rol,
            texto = texto,
            fecha = System.currentTimeMillis()
        ))

    override fun observarCantidadPorEstado(estado: EstadoTicket): Flow<Int> =
        tickets.contarPorEstado(estado.name)
    override fun observarCantidadPorCategoria(): Flow<List<ConteoCategoria>> = tickets.contarPorCategoria()
    override fun observarAltasSinAtender(limite: Long): Flow<Int> = tickets.contarAltasSinAtender(limite)
}
