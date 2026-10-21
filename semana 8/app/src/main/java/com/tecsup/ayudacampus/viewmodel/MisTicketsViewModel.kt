package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import kotlinx.coroutines.flow.*

class MisTicketsViewModel(repo: AyudaRepository, val usuario: Usuario) : ViewModel() {
    private val _estado = MutableStateFlow<EstadoTicket?>(null)
    val estado = _estado.asStateFlow()
    private val _busqueda = MutableStateFlow("")
    val busqueda = _busqueda.asStateFlow()
    private val todos = repo.observarTicketsCliente(usuario.id)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val abiertos = todos.map { lista -> lista.count { it.ticket.estado == EstadoTicket.ABIERTO.name } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val tickets = combine(todos, estado, busqueda) { lista, filtro, texto ->
        val consulta = texto.trim()
        lista.filter {
            (filtro == null || it.ticket.estado == filtro.name) &&
                (consulta.isEmpty() || it.ticket.titulo.contains(consulta, ignoreCase = true) ||
                    "#${it.ticket.id.toString().padStart(4, '0')}".contains(consulta))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun seleccionarEstado(estado: EstadoTicket?) { _estado.value = estado }
    fun buscar(texto: String) { _busqueda.value = texto }
}
