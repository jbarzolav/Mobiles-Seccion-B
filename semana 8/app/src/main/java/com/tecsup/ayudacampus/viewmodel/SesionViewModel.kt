package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SesionViewModel(
    private val repo: AyudaRepository,
    private val preferencias: PreferenciasStore
) : ViewModel() {
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual = _usuarioActual.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private val _lista = MutableStateFlow(false)
    val lista = _lista.asStateFlow()
    private val _cargando = MutableStateFlow(false)
    val cargando = _cargando.asStateFlow()
    val modoOscuro = preferencias.modoOscuro.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    init {
        viewModelScope.launch {
            try {
                val rol = preferencias.perfilActivo.first()
                val id = preferencias.usuarioId.first()
                if (rol.isNotEmpty()) {
                    val usuario = repo.obtenerUsuario(id)
                    if (usuario != null && usuario.rol == rol && Rol.entries.any { it.name == rol }) {
                        _usuarioActual.value = usuario
                    } else preferencias.cerrarSesion()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _error.value = "No se pudo recuperar la sesión. Intenta ingresar nuevamente."
            } finally {
                _lista.value = true
            }
        }
    }

    fun login(correo: String, clave: String, rolSeleccionado: Rol) {
        if (_cargando.value || !_lista.value) return
        _error.value = null
        if (correo.isBlank() || clave.isBlank()) {
            _error.value = "Completa el correo y la contraseña"
            return
        }
        _cargando.value = true
        viewModelScope.launch {
            try {
                val usuario = repo.login(correo.trim(), clave)
                when {
                    usuario == null -> _error.value = "Correo o contraseña incorrectos"
                    usuario.rol != rolSeleccionado.name -> _error.value = "El usuario no corresponde al perfil seleccionado"
                    else -> {
                        preferencias.guardarSesion(rolSeleccionado, usuario.id)
                        _usuarioActual.value = usuario
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _error.value = "No se pudo iniciar sesión. Inténtalo nuevamente."
            } finally {
                _cargando.value = false
            }
        }
    }
}
