package com.example.serviciorest.repository

import com.example.serviciorest.data.model.Post
import com.example.serviciorest.data.model.Producto
import com.example.serviciorest.data.remote.RetrofitInstance
import com.example.serviciorest.data.remote.RetrofitInstanceApi2

class ProductoRepository {
    suspend fun getProductos(): List<Producto>{
        return RetrofitInstanceApi2.api.getProductos()
    }

    suspend fun getPostById(id: Int): Producto {
        return RetrofitInstanceApi2.api.getProductoById(id)
    }
}