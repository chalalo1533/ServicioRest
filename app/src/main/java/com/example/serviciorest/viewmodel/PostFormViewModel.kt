package com.example.serviciorest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serviciorest.data.model.Post
import com.example.serviciorest.data.model.PostErrores
import com.example.serviciorest.data.model.PostUiState
import com.example.serviciorest.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostFormViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _estado = MutableStateFlow(value = PostUiState())
    val estado: StateFlow<PostUiState> = _estado

    // Id del post ya cargado (evita recargar al rotar la pantalla)
    private var idCargado: Int? = null

    // Actualiza el campo userId
    fun onUserIdChange(valor: String) {
        _estado.update { it.copy(userId = valor, errores = it.errores.copy(userId = null)) }
    }

    // Actualiza el campo título
    fun onTitleChange(valor: String) {
        _estado.update { it.copy(title = valor, errores = it.errores.copy(title = null)) }
    }

    // Actualiza el campo contenido
    fun onBodyChange(valor: String) {
        _estado.update { it.copy(body = valor, errores = it.errores.copy(body = null)) }
    }

    // Modo edición: trae el post con GET /posts/{id} para rellenar el formulario
    fun cargarPost(id: Int) {
        if (idCargado == id) return
        _estado.update { it.copy(id = id, cargando = true, errorServidor = null) }

        viewModelScope.launch {
            try {
                val post = repository.getPostById(id)
                idCargado = id
                _estado.update {
                    it.copy(
                        userId = post.userId.toString(),
                        title = post.title,
                        body = post.body,
                        cargando = false
                    )
                }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(cargando = false, errorServidor = "No se pudo cargar el post: ${e.localizedMessage}")
                }
            }
        }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val userIdNumero = estadoActual.userId.trim().toIntOrNull()
        val errores = PostErrores(
            userId = if (userIdNumero == null || userIdNumero <= 0) "Debe ser un número mayor a 0" else null,
            title = if (estadoActual.title.trim().length < 3) "Debe tener al menos 3 caracteres" else null,
            body = if (estadoActual.body.trim().length < 10) "Debe tener al menos 10 caracteres" else null
        )

        val hayErrores = listOfNotNull(
            errores.userId,
            errores.title,
            errores.body
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    // Valida y envía: POST si es nuevo, PUT si es edición
    fun guardar() {
        if (!validarFormulario()) return
        val actual = _estado.value

        viewModelScope.launch {
            _estado.update { it.copy(cargando = true, errorServidor = null) }
            try {
                val post = Post(
                    userId = actual.userId.trim().toInt(),
                    id = actual.id ?: 0, // en un POST el servidor asigna el id real
                    title = actual.title.trim(),
                    body = actual.body.trim()
                )
                val respuesta = if (actual.id == null) {
                    repository.createPost(post)
                } else {
                    repository.updatePost(actual.id, post)
                }
                _estado.update { it.copy(cargando = false, respuesta = respuesta) }
            } catch (e: Exception) {
                _estado.update {
                    it.copy(cargando = false, errorServidor = "Error al guardar: ${e.localizedMessage}")
                }
            }
        }
    }
}
