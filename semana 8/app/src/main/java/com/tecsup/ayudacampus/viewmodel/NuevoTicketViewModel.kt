package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Prioridad
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class FormularioTicket(
    val categoriaId: Int? = null,
    val titulo: String = "",
    val ubicacion: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val descripcion: String = "",
    val errorCategoria: String? = null,
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val error: String? = null,
    val enviando: Boolean = false,
    val enviado: Boolean = false
)

class NuevoTicketViewModel(private val repo: AyudaRepository, private val usuario: Usuario) : ViewModel() {
    val categorias = repo.observarCategoriasActivas()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    private val _formulario = MutableStateFlow(FormularioTicket())
    val formulario = _formulario.asStateFlow()

    private fun editar(cambio: (FormularioTicket) -> FormularioTicket) {
        _formulario.update { if (it.enviando || it.enviado) it else cambio(it).copy(error = null) }
    }
    fun categoria(id: Int) = editar { it.copy(categoriaId = id, errorCategoria = null) }
    fun titulo(valor: String) = editar { it.copy(titulo = valor, errorTitulo = null) }
    fun ubicacion(valor: String) = editar { it.copy(ubicacion = valor) }
    fun prioridad(valor: Prioridad) = editar { it.copy(prioridad = valor) }
    fun descripcion(valor: String) = editar { it.copy(descripcion = valor, errorDescripcion = null) }

    fun enviar() {
        val datos = _formulario.value
        if (datos.enviando || datos.enviado) return
        val validado = datos.copy(
            errorTitulo = if (datos.titulo.trim().length < 5) "El título debe tener al menos 5 caracteres" else null,
            errorDescripcion = if (datos.descripcion.trim().length < 10) "La descripción debe tener al menos 10 caracteres" else null,
            errorCategoria = if (datos.categoriaId == null) "Selecciona una categoría" else null,
            error = null
        )
        _formulario.value = validado
        if (validado.errorTitulo != null || validado.errorDescripcion != null || validado.errorCategoria != null) return
        _formulario.update { it.copy(enviando = true) }
        viewModelScope.launch {
            try {
                if (repo.observarCategoriasActivas().first().none { it.id == datos.categoriaId }) {
                    _formulario.update { it.copy(errorCategoria = "Selecciona una categoría activa") }
                    return@launch
                }
                repo.crearTicket(usuario.id, requireNotNull(datos.categoriaId), datos.titulo.trim(),
                    datos.descripcion.trim(), datos.ubicacion.trim(), datos.prioridad)
                _formulario.update { it.copy(enviado = true) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _formulario.update { it.copy(error = "No se pudo enviar el ticket. Inténtalo nuevamente.") }
            } finally {
                _formulario.update { it.copy(enviando = false) }
            }
        }
    }
}
