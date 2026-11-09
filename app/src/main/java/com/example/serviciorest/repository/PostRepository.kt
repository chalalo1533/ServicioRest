package com.example.serviciorest.repository

import com.example.serviciorest.data.model.Post
import com.example.serviciorest.data.remote.RetrofitInstance

class PostRepository {
    suspend fun getPosts(): List<Post>{
        return RetrofitInstance.api.getPost()
    }

    suspend fun getPostById(id: Int): Post {
        return RetrofitInstance.api.getPostById(id)
    }

    suspend fun createPost(post: Post): Post {
        return RetrofitInstance.api.createPost(post)
    }

    suspend fun updatePost(id: Int, post: Post): Post {
        return RetrofitInstance.api.updatePost(id, post)
    }

    // Devuelve el código HTTP; si no fue exitoso lanza una excepción
    suspend fun deletePost(id: Int): Int {
        val response = RetrofitInstance.api.deletePost(id)
        if (!response.isSuccessful) {
            throw Exception("HTTP ${response.code()}")
        }
        return response.code()
    }
}
