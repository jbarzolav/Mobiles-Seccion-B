package com.tecsup.ayudacampus.ui.navigation

object Rutas {
    const val NUEVO_TICKET = "nuevo_ticket"
    const val DETALLE_TICKET = "detalle_ticket/{id}"
    const val PERFIL = "perfil"
    fun detalleTicket(id: Int) = "detalle_ticket/$id"
    const val LOGIN = "login"
    const val MIS_TICKETS = "mis_tickets"
    const val PANEL = "panel"
    const val SPLASH = "splash"
    const val CATEGORIAS = "categorias"
}
