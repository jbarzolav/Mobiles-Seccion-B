package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.local.entity.Usuario

class AyudaViewModelFactory(
    private val repo: AyudaRepository,
    private val preferencias: PreferenciasStore,
    private val usuario: Usuario? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            CategoriasViewModel::class.java -> CategoriasViewModel(repo)
            SesionViewModel::class.java -> SesionViewModel(repo, preferencias)
            MisTicketsViewModel::class.java -> MisTicketsViewModel(repo, requireNotNull(usuario))
            else -> error("ViewModel no configurado: ${modelClass.name}")
        } as T
    }
}
