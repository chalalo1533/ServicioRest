package com.example.serviciorest.data.remote

import com.example.serviciorest.data.model.Post
import com.example.serviciorest.data.model.Producto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiProductos {
    @GET("/productos")
    suspend fun getProductos(): List<Producto>

    @GET("/productos/{id}")
    suspend fun getProductoById(@Path("id") id: Int): Producto
}
