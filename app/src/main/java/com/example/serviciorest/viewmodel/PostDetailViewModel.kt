package com.example.serviciorest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serviciorest.data.model.Post
import com.example.serviciorest.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostDetailViewModel : ViewModel() {

    private val repository = PostRepository()

    // Post seleccionado (null mientras no se ha cargado)
    private val _post = MutableStateFlow<Post?>(value = null)
    val post: StateFlow<Post?> = _post

    // Indica si la llamada REST está en curso
    private val _isLoading = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Mensaje de error (null si no hay error)
    private val _error = MutableStateFlow<String?>(value = null)
    val error: StateFlow<String?> = _error

    // Segunda llamada REST: obtiene todos los datos de un post por su id
    fun fetchPost(id: Int) {
        // Evita repetir la llamada si ya se cargó (por ejemplo al rotar la pantalla)
        if (_post.value?.id == id) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _post.value = repository.getPostById(id)
            } catch (e: Exception) {
                _error.value = "Error al obtener el detalle: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
