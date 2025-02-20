package com.example.simarropopandroid.network

import com.example.simarropopandroid.modelos.UsuarioApi
import retrofit2.Call
import retrofit2.http.GET

interface UsuarioApi {
    @GET("usuarios/buscar")
    fun obtenerUsuarios(): Call<List<UsuarioApi>>
}

