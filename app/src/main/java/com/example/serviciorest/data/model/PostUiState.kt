package com.example.serviciorest.data.model

// Estado completo del formulario de Post
data class PostUiState(
    val id: Int? = null,                    // null = nuevo (POST) / con valor = edición (PUT)
    val userId: String = "",                // Id del usuario (texto para el TextField)
    val title: String = "",                 // Título del post
    val body: String = "",                  // Contenido del post
    val errores: PostErrores = PostErrores(), // Errores por campo
    val cargando: Boolean = false,          // Llamada REST en curso
    val errorServidor: String? = null,      // Error devuelto por la API
    val respuesta: Post? = null             // Lo que respondió el servidor tras POST/PUT
)
