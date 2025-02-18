package com.example.simarropopandroid.network

import com.example.simarropopandroid.modelos.Producto
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("productos/listarProductos") // URL relativa correcta
    fun obtenerProductos(): Call<List<Producto>>
}
