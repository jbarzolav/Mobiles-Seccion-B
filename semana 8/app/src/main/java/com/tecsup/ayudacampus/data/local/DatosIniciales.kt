package com.tecsup.ayudacampus.data.local

import androidx.room.withTransaction
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.data.local.entity.Comentario
import com.tecsup.ayudacampus.data.local.entity.Ticket
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.model.EstadoTicket
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.model.Rol

object DatosIniciales {
    suspend fun sembrar(db: AyudaDatabase) {
        db.withTransaction {
            if (db.usuarioDao().contar() != 0) return@withTransaction

            val categorias = listOf(
                Categoria(nombre = "Hardware", icono = "pc"),
                Categoria(nombre = "Software", icono = "code"),
                Categoria(nombre = "Red / Wi-Fi", icono = "wifi"),
                Categoria(nombre = "Cuenta", icono = "key"),
                Categoria(nombre = "Impresión", icono = "printer")
            )
            val ids = categorias.map { db.categoriaDao().insertar(it).toInt() }
            db.usuarioDao().insertarTodos(listOf(
                Usuario(nombre = "Juan Pérez", correo = "juan@campus.test", clave = "1234", rol = Rol.CLIENTE.name),
                Usuario(nombre = "Soporte TI", correo = "soporte@campus.test", clave = "admin", rol = Rol.ADMIN.name)
            ))
            val cliente = checkNotNull(db.usuarioDao().login("juan@campus.test", "1234"))
            val soporte = checkNotNull(db.usuarioDao().login("soporte@campus.test", "admin"))
            val ahora = System.currentTimeMillis()
            val hora = 3_600_000L
            val tickets = listOf(
                Ticket(clienteId = cliente.id, categoriaId = ids[2], titulo = "Sin Wi-Fi en el laboratorio 3",
                    descripcion = "Desde ayer no hay señal de Wi-Fi en el laboratorio.", ubicacion = "Lab 3 · Pabellón B",
                    prioridad = Prioridad.ALTA.name, estado = EstadoTicket.EN_PROCESO.name, fecha = ahora - 2 * hora),
                Ticket(clienteId = cliente.id, categoriaId = ids[0], titulo = "Teclado no responde",
                    descripcion = "El teclado del equipo no reconoce ninguna tecla.", ubicacion = "Aula 204",
                    fecha = ahora - 24 * hora),
                Ticket(clienteId = cliente.id, categoriaId = ids[4], titulo = "No puedo imprimir en biblioteca",
                    descripcion = "Los documentos permanecen en la cola de impresión.", ubicacion = "Biblioteca",
                    prioridad = Prioridad.BAJA.name, estado = EstadoTicket.RESUELTO.name, fecha = ahora - 72 * hora),
                Ticket(clienteId = cliente.id, categoriaId = ids[0], titulo = "Equipo del aula no enciende",
                    descripcion = "El equipo no enciende al pulsar el botón de encendido.", ubicacion = "Aula 305",
                    prioridad = Prioridad.ALTA.name, fecha = ahora - 3 * hora),
                Ticket(clienteId = cliente.id, categoriaId = ids[1], titulo = "Instalar Android Studio",
                    descripcion = "Necesito Android Studio para realizar el laboratorio.", ubicacion = "Lab 2",
                    fecha = ahora - hora / 2),
                Ticket(clienteId = cliente.id, categoriaId = ids[3], titulo = "Cuenta del aula virtual bloqueada",
                    descripcion = "Mi cuenta quedó bloqueada y no permite iniciar sesión.", ubicacion = "Aula virtual",
                    prioridad = Prioridad.BAJA.name, estado = EstadoTicket.CERRADO.name,
                    fecha = ahora - 48 * hora, fechaCierre = ahora - 24 * hora, calificacion = 4)
            )
            val ticketIds = tickets.map { db.ticketDao().insertarTicket(it).toInt() }
            val comentarios = listOf(
                Comentario(ticketId = ticketIds[0], autorId = cliente.id, autorNombre = cliente.nombre,
                    autorRol = cliente.rol, texto = "La falla afecta a todos los equipos del laboratorio.", fecha = ahora - 2 * hora),
                Comentario(ticketId = ticketIds[0], autorId = soporte.id, autorNombre = soporte.nombre,
                    autorRol = soporte.rol, texto = "Estamos revisando el punto de acceso.", fecha = ahora - hora),
                Comentario(ticketId = ticketIds[2], autorId = soporte.id, autorNombre = soporte.nombre,
                    autorRol = soporte.rol, texto = "Reinstalamos el controlador. Ya debería imprimir.", fecha = ahora - 48 * hora),
                Comentario(ticketId = ticketIds[5], autorId = cliente.id, autorNombre = cliente.nombre,
                    autorRol = cliente.rol, texto = "Ya puedo ingresar, gracias por la atención.", fecha = ahora - 24 * hora)
            )
            comentarios.forEach { db.ticketDao().insertarComentario(it) }
        }
    }
}
