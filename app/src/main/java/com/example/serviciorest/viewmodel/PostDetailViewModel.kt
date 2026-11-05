package com.example.serviciorest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serviciorest.data.model.Post
import com.example.serviciorest.data.model.Producto
import com.example.serviciorest.repository.PostRepository
import com.example.serviciorest.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel() {

    private val repository = ProductoRepository()

    // Post seleccionado (null mientras no se ha cargado)
    private val _prd = MutableStateFlow<Producto?>(value = null)
    val prd: StateFlow<Producto?> = _prd

    // Indica si la llamada REST está en curso
    private val _isLoading = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Mensaje de error (null si no hay error)
    private val _error = MutableStateFlow<String?>(value = null)
    val error: StateFlow<String?> = _error


    fun fetchProducto(id: Int) {

        if (_prd.value?.id == id) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _prd.value = repository.getPostById(id)
            } catch (e: Exception) {
                _error.value = "Error al obtener el detalle: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
