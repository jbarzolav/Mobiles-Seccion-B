package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AtenderViewModel(
    private val repo: AyudaRepository,
    val usuario: Usuario,
    val ticketId: Int
) : ViewModel() {

    val ticket: StateFlow<TicketConDetalle?> = repo.observarTicket(ticketId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _estadoSeleccionado = MutableStateFlow<EstadoTicket?>(null)
    val estadoSeleccionado: StateFlow<EstadoTicket?> = _estadoSeleccionado.asStateFlow()

    private val _prioridadSeleccionada = MutableStateFlow<Prioridad?>(null)
    val prioridadSeleccionada: StateFlow<Prioridad?> = _prioridadSeleccionada.asStateFlow()

    private val _guardado = MutableStateFlow(false)
    val guardado: StateFlow<Boolean> = _guardado.asStateFlow()

    init {
        viewModelScope.launch {
            ticket.collect { t ->
                if (t != null) {
                    if (_estadoSeleccionado.value == null) {
                        _estadoSeleccionado.value = EstadoTicket.valueOf(t.ticket.estado)
                    }
                    if (_prioridadSeleccionada.value == null) {
                        _prioridadSeleccionada.value = Prioridad.valueOf(t.ticket.prioridad)
                    }
                }
            }
        }
    }

    /** Regla: Soporte solo puede elegir ABIERTO, EN_PROCESO o RESUELTO. CERRADO deshabilitado.
     * No se puede cambiar el estado de un ticket CERRADO. */
    fun cambiarEstado(estado: EstadoTicket) {
        val t = ticket.value ?: return
        val estadoActual = EstadoTicket.valueOf(t.ticket.estado)
        if (estadoActual == EstadoTicket.CERRADO) return
        if (estado == EstadoTicket.CERRADO) return
        _estadoSeleccionado.value = estado
    }

    fun cambiarPrioridad(p: Prioridad) {
        val t = ticket.value ?: return
        val estadoActual = EstadoTicket.valueOf(t.ticket.estado)
        if (estadoActual == EstadoTicket.CERRADO) return
        _prioridadSeleccionada.value = p
    }

    fun responder(texto: String) {
        val t = ticket.value ?: return
        if (texto.isBlank()) return
        viewModelScope.launch {
            repo.agregarComentario(t.ticket.id, usuario, texto.trim())
        }
    }

    fun guardar() {
        val t = ticket.value ?: return
        val estadoActual = EstadoTicket.valueOf(t.ticket.estado)
        if (estadoActual == EstadoTicket.CERRADO) return
        val nuevoEstado = _estadoSeleccionado.value ?: estadoActual
        val nuevaPrioridad = _prioridadSeleccionada.value ?: Prioridad.valueOf(t.ticket.prioridad)

        viewModelScope.launch {
            repo.cambiarEstado(t.ticket.id, nuevoEstado)
            repo.cambiarPrioridad(t.ticket.id, nuevaPrioridad)
            _guardado.value = true
        }
    }
}
