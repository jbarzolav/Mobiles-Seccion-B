package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.ui.components.graficos.DatoGrafico
import com.tecsup.ayudacampus.ui.theme.EstadoAbierto
import com.tecsup.ayudacampus.ui.theme.EstadoCerrado
import com.tecsup.ayudacampus.ui.theme.EstadoEnProceso
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PanelViewModel(private val repo: AyudaRepository) : ViewModel() {

    val abiertos: StateFlow<Int> = contar(EstadoTicket.ABIERTO)
    val enProceso: StateFlow<Int> = contar(EstadoTicket.EN_PROCESO)
    val resueltos: StateFlow<Int> = contar(EstadoTicket.RESUELTO)

    val tiempoPromedioResolucion: StateFlow<Long> = flow {
        emit(repo.obtenerTiempoPromedioResolucion())
    }.stateInEnPantalla(0L)

    /** Convierte los conteos de Room al formato que esperan los gráficos. */
    val porEstado: StateFlow<List<DatoGrafico>> = combine(
        repo.observarCantidadPorEstado(EstadoTicket.ABIERTO),
        repo.observarCantidadPorEstado(EstadoTicket.EN_PROCESO),
        repo.observarCantidadPorEstado(EstadoTicket.RESUELTO),
        repo.observarCantidadPorEstado(EstadoTicket.CERRADO)
    ) { abierto, proceso, resuelto, cerrado ->
        listOf(
            DatoGrafico("Abierto", abierto.toFloat(), EstadoAbierto),
            DatoGrafico("En proceso", proceso.toFloat(), EstadoEnProceso),
            DatoGrafico("Resuelto", resuelto.toFloat(), EstadoResuelto),
            DatoGrafico("Cerrado", cerrado.toFloat(), EstadoCerrado)
        )
    }.stateInEnPantalla(emptyList())

    val porCategoria: StateFlow<List<DatoGrafico>> = repo.observarCantidadPorCategoria()
        .map { conteos -> conteos.map { DatoGrafico(it.categoria, it.cantidad.toFloat()) } }
        .stateInEnPantalla(emptyList())

    /** Prioridad Alta que nadie ha atendido durante más de una hora. */
    val altasSinAtender: StateFlow<Int> = repo
        .observarAltasSinAtender(System.currentTimeMillis() - UNA_HORA)
        .stateInEnPantalla(0)

    private fun contar(estado: EstadoTicket): StateFlow<Int> =
        repo.observarCantidadPorEstado(estado).stateInEnPantalla(0)

    private fun <T> Flow<T>.stateInEnPantalla(inicio: T): StateFlow<T> =
        stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), inicio)

    private companion object {
        const val UNA_HORA = 3_600_000L
    }
}
