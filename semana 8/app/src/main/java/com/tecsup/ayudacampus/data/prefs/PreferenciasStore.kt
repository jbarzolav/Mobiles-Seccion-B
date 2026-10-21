package com.tecsup.ayudacampus.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.tecsup.ayudacampus.model.Rol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("ajustes")

class PreferenciasStore(context: Context) {
    private val store = context.applicationContext.dataStore

    private object Claves {
        val perfilActivo = stringPreferencesKey("perfilActivo")
        val usuarioId = intPreferencesKey("usuarioId")
        val modoOscuro = booleanPreferencesKey("modoOscuro")
        val notificaciones = booleanPreferencesKey("notificaciones")
        val recibirTickets = booleanPreferencesKey("recibirTickets")
    }

    val perfilActivo: Flow<String> = store.data.map { it[Claves.perfilActivo] ?: "" }
    val usuarioId: Flow<Int> = store.data.map { it[Claves.usuarioId] ?: 0 }
    val modoOscuro: Flow<Boolean> = store.data.map { it[Claves.modoOscuro] ?: false }
    val notificaciones: Flow<Boolean> = store.data.map { it[Claves.notificaciones] ?: true }
    val recibirTickets: Flow<Boolean> = store.data.map { it[Claves.recibirTickets] ?: true }

    suspend fun guardarSesion(rol: Rol, usuarioId: Int) {
        store.edit {
            it[Claves.perfilActivo] = rol.name
            it[Claves.usuarioId] = usuarioId
        }
    }

    suspend fun guardarModoOscuro(b: Boolean) {
        store.edit { it[Claves.modoOscuro] = b }
    }

    suspend fun guardarNotificaciones(b: Boolean) {
        store.edit { it[Claves.notificaciones] = b }
    }

    suspend fun guardarRecibirTickets(b: Boolean) {
        store.edit { it[Claves.recibirTickets] = b }
    }

    suspend fun cerrarSesion() {
        store.edit {
            it.remove(Claves.perfilActivo)
            it.remove(Claves.usuarioId)
        }
    }
}
