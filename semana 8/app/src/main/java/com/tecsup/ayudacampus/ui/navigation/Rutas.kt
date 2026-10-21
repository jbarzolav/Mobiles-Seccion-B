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
    const val BANDEJA = "bandeja"
    const val ATENDER_TICKET = "atender_ticket/{id}"
    fun atenderTicket(id: Int) = "atender_ticket/$id"
    const val FORMULARIO_CATEGORIA = "formulario_categoria?id={id}"
    fun formularioCategoria(id: Int? = null) = if (id != null) "formulario_categoria?id=$id" else "formulario_categoria"
}
