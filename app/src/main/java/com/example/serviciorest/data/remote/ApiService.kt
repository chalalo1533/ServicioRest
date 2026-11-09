package com.example.serviciorest.data.remote
import com.example.serviciorest.data.model.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
interface ApiService {
    // Listado: obtiene todos los posts
    @GET("/posts")
    suspend fun getPost(): List<Post>

    // Detalle: obtiene un post específico por su id
    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    // Crear: envía un post nuevo en el cuerpo (JSON)
    @POST("/posts")
    suspend fun createPost(@Body post: Post): Post

    // Actualizar: reemplaza el post con ese id
    @PUT("/posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: Post): Post

    // Eliminar: no devuelve cuerpo, solo nos interesa el código HTTP
    @DELETE("/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}
