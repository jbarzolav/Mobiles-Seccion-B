package com.tuapp.clinica.tecsupfit.navigation

sealed class Routes(val route: String) {
    data object Inicio : Routes("inicio")
    data object DetalleClase : Routes("detalle_clase/{claseId}") {
        fun create(claseId: Int) = "detalle_clase/$claseId"
    }
    data object ConfirmacionReserva : Routes("confirmacion_reserva/{claseId}/{fecha}/{hora}") {
        fun create(claseId: Int, fecha: String, hora: String) =
            "confirmacion_reserva/$claseId/$fecha/$hora"
    }
    data object Reservas : Routes("reservas")
    data object Rutinas : Routes("rutinas")
    data object Perfil : Routes("perfil")
}
