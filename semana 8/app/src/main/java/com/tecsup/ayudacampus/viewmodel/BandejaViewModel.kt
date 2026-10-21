package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BandejaViewModel(private val repo: AyudaRepository) : ViewModel() {

    private val filtro = MutableStateFlow<EstadoTicket?>(null)

    /** El chip elige el filtro; el orden por prioridad es el que entrega el DAO. */
    val tickets: StateFlow<List<TicketConDetalle>> =
        combine(repo.observarTodosLosTickets(), filtro) { lista, estado ->
            if (estado == null) lista else lista.filter { it.ticket.estado == estado.name }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val estadoSeleccionado: StateFlow<EstadoTicket?> = filtro.asStateFlow()

    fun filtrarPor(estado: EstadoTicket?) {
        filtro.value = estado
    }
}
