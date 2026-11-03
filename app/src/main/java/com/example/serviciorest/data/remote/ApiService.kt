package com.example.serviciorest.data.remote
import com.example.serviciorest.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path
interface ApiService {
    @GET("/posts")
    suspend fun getPost(): List<Post>

    // Detalle: obtiene un post específico por su id
    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post
}


