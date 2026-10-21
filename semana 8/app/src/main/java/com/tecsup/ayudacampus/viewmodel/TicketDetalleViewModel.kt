package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TicketDetalleViewModel(
    private val repo: AyudaRepository,
    val usuario: Usuario,
    ticketId: Int
) : ViewModel() {

    val ticket: StateFlow<TicketConDetalle?> = repo.observarTicket(ticketId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    /** Regla: nada que comentar si el texto está vacío o el ticket ya está cerrado. */
    fun comentar(texto: String) {
        val actual = ticket.value ?: return
        if (texto.isBlank()) return
        if (actual.ticket.estado == EstadoTicket.CERRADO.name) return
        viewModelScope.launch { repo.agregarComentario(actual.ticket.id, usuario, texto.trim()) }
    }

    /** Regla: solo cierra tickets RESUELTOS y exige calificación de 1 a 5. */
    fun cerrar(calificacion: Int, comentarioFinal: String = "") {
        val actual = ticket.value ?: return
        if (actual.ticket.estado != EstadoTicket.RESUELTO.name) return
        if (calificacion !in 1..5) return
        viewModelScope.launch {
            if (comentarioFinal.isNotBlank()) {
                repo.agregarComentario(actual.ticket.id, usuario, comentarioFinal.trim())
            }
            repo.cerrarConCalificacion(actual.ticket.id, calificacion)
        }
    }

    /** Regla: solo se reabren tickets RESUELTOS. */
    fun reabrir() {
        val actual = ticket.value ?: return
        if (actual.ticket.estado != EstadoTicket.RESUELTO.name) return
        viewModelScope.launch { repo.reabrir(actual.ticket.id) }
    }
}
