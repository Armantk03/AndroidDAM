package com.example.simarropopandroid.network

import com.example.simarropopandroid.modelos.Categoria
import com.example.simarropopandroid.modelos.Foto
import com.example.simarropopandroid.modelos.Producto
import com.example.simarropopandroid.modelos.UsuarioApi
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("productos/listarProductos")
    fun obtenerProductos(): Call<List<Producto>>

    @GET("categorias")
    fun obtenerCategorias(): Call<List<Categoria>>

    @POST("productos")
    fun registrarProducto(@Body producto: Producto): Call<Producto>

    @POST("fotos/producto/{idProducto}")
    fun registrarFoto(
        @Path("idProducto") idProducto: Int,
        @Body foto: Foto
    ): Call<Foto>
}

