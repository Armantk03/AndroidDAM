package com.example.simarropopandroid.network

import ValoracionApi
import com.example.simarropopandroid.modelos.UsuarioApi
import com.example.simarropopandroid.modelos.ValoracionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioApi {

    @GET("usuarios/buscar")
    fun obtenerUsuarios(): Call<List<UsuarioApi>>

    @GET("usuarios/comprobar")
    fun iniciarSesion(
        @Query("correo") correo: String,
        @Query("contrasenya") contrasenya: String
    ): Call<UsuarioApi?>  // Se ajusta para que devuelva un usuario o null

    @POST("usuarios")
    fun registrarUsuario(@Body usuario: UsuarioApi): Call<UsuarioApi>

    @GET("usuarios/{id}")
    fun listarPorId(@Path("id") id: Int): Call<UsuarioApi>

    @PUT("usuarios/{id}")
    fun modificarUsuario(@Path("id") id: Int, @Body usuario: UsuarioApi): Call<UsuarioApi>

    @POST("usuarios/hacerPremium")
    fun hacerPremium(@Query("correo") correo: String): Call<String>

    @GET("valoraciones/{idUsuario}")
    fun obtenerValoracion(@Path("idUsuario") idUsuario: Int): Call<List<ValoracionApi>>

    @DELETE("usuarios/{id}")
    fun eliminarUsuario(@Path("id") id: Int): Call<Void>






}
