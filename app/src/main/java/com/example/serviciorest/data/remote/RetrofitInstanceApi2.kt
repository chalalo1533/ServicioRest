package com.example.serviciorest.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceApi2 {
    val api: ApiProductos by lazy {
        Retrofit.Builder()
            .baseUrl("https://f7eb-179-56-215-178.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiProductos::class.java)
    }

}

