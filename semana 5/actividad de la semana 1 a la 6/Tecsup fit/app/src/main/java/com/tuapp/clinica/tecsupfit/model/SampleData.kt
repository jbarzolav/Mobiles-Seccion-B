package com.tuapp.clinica.tecsupfit.model

object SampleData {

    val filtros = listOf("Hoy", "Esta semana")

    val clases = listOf(
        Clase(
            id = 1,
            nombre = "Yoga funcional",
            hora = "7:00 am",
            sala = "Sala 2",
            duracion = "50 min",
            descripcion = "Sesión de yoga para mejorar flexibilidad y equilibrio. Cupos limitados.",
            cuposDisponibles = 6,
            cuposTotal = 15
        ),
        Clase(
            id = 2,
            nombre = "Cross Training",
            hora = "6:00 pm",
            sala = "Sala 1",
            duracion = "45 min",
            descripcion = "Entrenamiento funcional de alta intensidad. Cupos limitados.",
            cuposDisponibles = 8,
            cuposTotal = 12
        ),
        Clase(
            id = 3,
            nombre = "Spinning",
            hora = "7:30 pm",
            sala = "Sala 3",
            duracion = "40 min",
            descripcion = "Clase de ciclismo indoor con música motivadora. Cupos limitados.",
            cuposDisponibles = 4,
            cuposTotal = 20
        ),
        Clase(
            id = 4,
            nombre = "Pilates",
            hora = "8:00 am",
            sala = "Sala 2",
            duracion = "55 min",
            descripcion = "Pilates mat para fortalecer el core y mejorar la postura.",
            cuposDisponibles = 10,
            cuposTotal = 14
        )
    )

    val reservas = listOf(
        Reserva(
            clase = "Cross Training",
            fecha = "Hoy",
            hora = "6:00 pm",
            sala = "Sala 1",
            estado = EstadoReserva.CONFIRMADA
        ),
        Reserva(
            clase = "Yoga funcional",
            fecha = "Ayer",
            hora = "7:00 am",
            sala = "Sala 2",
            estado = EstadoReserva.COMPLETADA
        )
    )

    val rutinas = listOf(
        Rutina(id = 1, nombre = "Full body principiante", dias = "Lun - Mié - Vie", ejercicios = 8),
        Rutina(id = 2, nombre = "Empuje y tirón", dias = "Mar - Jue", ejercicios = 10),
        Rutina(id = 3, nombre = "Movilidad y core", dias = "Sáb", ejercicios = 6)
    )

    val usuario = mapOf(
        "nombre" to "Diego Ramos",
        "plan" to "Plan Premium",
        "iniciales" to "DR",
        "clases" to "14",
        "rachas" to "3"
    )
}
