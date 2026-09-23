package com.tuapp.clinica.salud.model

object SampleData {

    val especialidades = listOf("Cardiología", "Pediatría", "Dermatología")

    val medicos = listOf(
        Medico(
            id = 1,
            nombre = "Dra. Ana Torres",
            especialidad = "Cardiología",
            calificacion = 4.9,
            anosExperiencia = 12,
            descripcion = "Especialista en arritmias e hipertensión, formada en la Clínica Mayo."
        ),
        Medico(
            id = 2,
            nombre = "Dr. Luis Vega",
            especialidad = "Pediatría",
            calificacion = 4.7,
            anosExperiencia = 8,
            descripcion = "Pediatra con enfoque en medicina preventiva y salud infantil."
        ),
        Medico(
            id = 3,
            nombre = "Dra. Rosa Díaz",
            especialidad = "Dermatología",
            calificacion = 4.8,
            anosExperiencia = 10,
            descripcion = "Dermatóloga especializada en tratamientos faciales y dermatología clínica."
        ),
        Medico(
            id = 4,
            nombre = "Dr. Carlos Mena",
            especialidad = "Cardiología",
            calificacion = 4.6,
            anosExperiencia = 15,
            descripcion = "Cardiólogo intervencionista con experiencia en cateterismos."
        ),
        Medico(
            id = 5,
            nombre = "Dra. Sofía Ríos",
            especialidad = "Pediatría",
            calificacion = 4.9,
            anosExperiencia = 6,
            descripcion = "Pediatra neonatal, atención integral del recién nacido."
        )
    )

    val citasAgendadas = listOf(
        Cita(
            medico = "Dra. Ana Torres",
            especialidad = "Cardiología",
            fecha = "Viernes 27",
            hora = "10:30 am",
            estado = EstadoCita.CONFIRMADA
        ),
        Cita(
            medico = "Dr. Luis Vega",
            especialidad = "Pediatría",
            fecha = "Miércoles 15",
            hora = "3:00 pm",
            estado = EstadoCita.COMPLETADA
        )
    )
}
