package com.tecsup.ayudacampus

import android.app.Application
import com.tecsup.ayudacampus.data.local.AyudaDatabase
import com.tecsup.ayudacampus.data.local.DatosIniciales
import com.tecsup.ayudacampus.data.repository.AyudaRepository
import com.tecsup.ayudacampus.data.repository.AyudaRepositoryRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AyudaApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val database: AyudaDatabase by lazy { AyudaDatabase.obtener(this) }

    val repository: AyudaRepository by lazy {
        AyudaRepositoryRoom(database.categoriaDao(), database.usuarioDao(), database.ticketDao())
    }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch { DatosIniciales.sembrar(database) }
    }
}
