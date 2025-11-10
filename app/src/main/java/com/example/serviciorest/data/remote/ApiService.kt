package com.example.serviciorest.data.remote
import com.example.serviciorest.data.model.Post
import retrofit2.http.GET
interface ApiService {
    @GET("/posts")
    suspend fun getPost(): List<Post>
}


