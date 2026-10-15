package com.tecsup.ayudacampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.ayudacampus.data.repository.AyudaRepository

class AyudaViewModelFactory(private val repo: AyudaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == CategoriasViewModel::class.java) { "ViewModel no configurado: ${modelClass.name}" }
        @Suppress("UNCHECKED_CAST")
        return CategoriasViewModel(repo) as T
    }
}
