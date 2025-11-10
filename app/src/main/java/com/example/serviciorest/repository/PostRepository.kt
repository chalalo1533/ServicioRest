package com.example.serviciorest.repository

import com.example.serviciorest.data.model.Post
import com.example.serviciorest.data.remote.RetrofitInstance

class PostRepository {
    suspend fun getPosts(): List<Post>{
        return RetrofitInstance.api.getPost()
    }
}