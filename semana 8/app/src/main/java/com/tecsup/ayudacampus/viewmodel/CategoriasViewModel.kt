package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriasViewModel(private val repo: AyudaRepository) : ViewModel() {
    val categorias: StateFlow<List<Categoria>> = repo.observarCategorias()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje.asStateFlow()

    fun limpiarMensaje() {
        _mensaje.value = null
    }

    fun alternar(c: Categoria) {
        viewModelScope.launch { repo.cambiarActiva(c.id, !c.activa) }
    }

    fun guardar(c: Categoria, onExito: () -> Unit = {}) {
        val nombreLimpio = c.nombre.trim()
        if (nombreLimpio.isEmpty()) {
            _mensaje.value = "El nombre es obligatorio"
            return
        }
        viewModelScope.launch {
            repo.guardarCategoria(c.copy(nombre = nombreLimpio))
            onExito()
        }
    }

    fun eliminar(c: Categoria, onExito: () -> Unit = {}) {
        viewModelScope.launch {
            val conteos = repo.observarCantidadPorCategoria().first()
            val tieneTickets = conteos.any { it.categoria == c.nombre && it.cantidad > 0 } ||
                    repo.observarTodosLosTickets().first().any { it.categoria.id == c.id }
            if (tieneTickets) {
                _mensaje.value = "No se puede eliminar una categoría que ya tiene tickets"
            } else {
                repo.eliminarCategoria(c)
                onExito()
            }
        }
    }
}
