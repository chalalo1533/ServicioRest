package com.example.serviciorest.data.model

// Errores de validación por campo del formulario (null = sin error)
data class PostErrores(
    val userId: String? = null,
    val title: String? = null,
    val body: String? = null
)
