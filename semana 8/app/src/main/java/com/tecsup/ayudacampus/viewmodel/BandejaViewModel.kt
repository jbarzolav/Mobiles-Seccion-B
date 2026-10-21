package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.relation.TicketConDetalle
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class BandejaViewModel(private val repo: AyudaRepository) : ViewModel() {

    private val _estado = MutableStateFlow<EstadoTicket?>(null)
    val estadoSeleccionado: StateFlow<EstadoTicket?> = _estado.asStateFlow()

    private val _prioridad = MutableStateFlow<Prioridad?>(null)
    val prioridadSeleccionada: StateFlow<Prioridad?> = _prioridad.asStateFlow()

    private val _categoriaId = MutableStateFlow<Int?>(null)
    val categoriaSeleccionada: StateFlow<Int?> = _categoriaId.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val categorias = repo.observarCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val tickets: StateFlow<List<TicketConDetalle>> = combine(
        repo.observarTodosLosTickets(),
        _estado,
        _prioridad,
        _categoriaId,
        _busqueda
    ) { lista, estado, prioridad, catId, query ->
        lista.filter { t ->
            val matchEstado = estado == null || t.ticket.estado == estado.name
            val matchPrioridad = prioridad == null || t.ticket.prioridad == prioridad.name
            val matchCategoria = catId == null || t.ticket.categoriaId == catId
            val matchQuery = query.isBlank() ||
                t.ticket.titulo.contains(query, ignoreCase = true) ||
                t.ticket.id.toString().contains(query) ||
                t.cliente.nombre.contains(query, ignoreCase = true)
            matchEstado && matchPrioridad && matchCategoria && matchQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun filtrarPor(estado: EstadoTicket?) {
        _estado.value = estado
    }

    fun filtrarPorPrioridad(prioridad: Prioridad?) {
        _prioridad.value = prioridad
    }

    fun filtrarPorCategoria(catId: Int?) {
        _categoriaId.value = catId
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }
}
