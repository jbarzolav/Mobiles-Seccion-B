package com.tuapp.clinica.salud.navigation

sealed class Routes(val route: String) {
    data object Inicio : Routes("inicio")
    data object PerfilMedico : Routes("perfil_medico/{medicoId}") {
        fun create(medicoId: Int) = "perfil_medico/$medicoId"
    }
    data object Agendar : Routes("agendar/{medicoId}/{fecha}/{hora}") {
        fun create(medicoId: Int, fecha: String, hora: String) =
            "agendar/$medicoId/$fecha/$hora"
    }
    data object Confirmacion : Routes("confirmacion/{medicoId}/{fecha}/{hora}") {
        fun create(medicoId: Int, fecha: String, hora: String) =
            "confirmacion/$medicoId/$fecha/$hora"
    }
    data object MisCitas : Routes("mis_citas")
    data object Historial : Routes("historial")
    data object Perfil : Routes("perfil")
}
