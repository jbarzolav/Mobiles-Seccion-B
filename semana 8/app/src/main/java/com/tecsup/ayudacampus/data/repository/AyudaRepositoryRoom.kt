package com.tecsup.ayudacampus.data.repository

import com.tecsup.ayudacampus.data.local.dao.CategoriaDao
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.model.ConteoCategoria
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.Flow

class AyudaRepositoryRoom(private val categorias: CategoriaDao) : AyudaRepository {
    override fun observarCategorias(): Flow<List<Categoria>> = categorias.observarTodas()
    override fun observarCategoriasActivas(): Flow<List<Categoria>> = categorias.observarActivas()
    override suspend fun cambiarActiva(id: Int, activa: Boolean) = categorias.cambiarActiva(id, activa)

    override suspend fun guardarCategoria(c: Categoria) {
        if (c.id == 0) categorias.insertar(c) else categorias.actualizar(c)
    }

    override suspend fun eliminarCategoria(c: Categoria) = categorias.eliminar(c)

    // Operaciones fuera del ejemplo de categorías: pendientes de los siguientes pasos.
    override suspend fun login(correo: String, clave: String): Usuario? = TODO("Pendiente del laboratorio")
    override suspend fun crearTicket(clienteId: Int, categoriaId: Int, titulo: String,
        descripcion: String, ubicacion: String, prioridad: Prioridad): Long = TODO("Pendiente del laboratorio")
    override fun observarTicketsCliente(clienteId: Int): Flow<List<TicketConDetalle>> = TODO("Pendiente del laboratorio")
    override fun observarTodosLosTickets(): Flow<List<TicketConDetalle>> = TODO("Pendiente del laboratorio")
    override fun observarTicket(id: Int): Flow<TicketConDetalle?> = TODO("Pendiente del laboratorio")
    override suspend fun cambiarEstado(ticketId: Int, estado: EstadoTicket): Unit = TODO("Pendiente del laboratorio")
    override suspend fun cambiarPrioridad(ticketId: Int, prioridad: Prioridad): Unit = TODO("Pendiente del laboratorio")
    override suspend fun cerrarConCalificacion(ticketId: Int, calificacion: Int): Unit = TODO("Pendiente del laboratorio")
    override suspend fun reabrir(ticketId: Int): Unit = TODO("Pendiente del laboratorio")
    override suspend fun agregarComentario(ticketId: Int, autor: Usuario, texto: String): Unit = TODO("Pendiente del laboratorio")
    override fun observarCantidadPorEstado(estado: EstadoTicket): Flow<Int> = TODO("Pendiente del laboratorio")
    override fun observarCantidadPorCategoria(): Flow<List<ConteoCategoria>> = TODO("Pendiente del laboratorio")
    override fun observarAltasSinAtender(limite: Long): Flow<Int> = TODO("Pendiente del laboratorio")
}
