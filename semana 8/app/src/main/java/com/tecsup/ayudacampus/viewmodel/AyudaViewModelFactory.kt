package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.data.prefs.PreferenciasStore
import com.tecsup.ayudacampus.data.local.entity.Usuario

class AyudaViewModelFactory(
    private val repo: AyudaRepository,
    private val preferencias: PreferenciasStore,
    private val usuario: Usuario? = null,
    private val ticketId: Int? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            CategoriasViewModel::class.java -> CategoriasViewModel(repo)
            SesionViewModel::class.java -> SesionViewModel(repo, preferencias)
            MisTicketsViewModel::class.java -> MisTicketsViewModel(repo, requireNotNull(usuario))
            NuevoTicketViewModel::class.java -> NuevoTicketViewModel(repo, requireNotNull(usuario))
            TicketDetalleViewModel::class.java ->
                TicketDetalleViewModel(repo, requireNotNull(usuario), requireNotNull(ticketId))
            AtenderViewModel::class.java ->
                AtenderViewModel(repo, requireNotNull(usuario), requireNotNull(ticketId))
            PanelViewModel::class.java -> PanelViewModel(repo)
            BandejaViewModel::class.java -> BandejaViewModel(repo)
            else -> error("ViewModel no configurado: ${modelClass.name}")
        } as T
    }
}
