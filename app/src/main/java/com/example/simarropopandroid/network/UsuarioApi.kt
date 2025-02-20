package com.example.simarropopandroid.network

import com.example.simarropopandroid.modelos.UsuarioApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UsuarioApi {

    @GET("usuarios/buscar")
    fun obtenerUsuarios(): Call<List<UsuarioApi>>

    @GET("usuarios/comprobar")
    fun iniciarSesion(
        @Query("correo") correo: String,
        @Query("contrasenya") contrasenya: String
    ): Call<UsuarioApi?>  // Se ajusta para que devuelva un usuario o null
}
