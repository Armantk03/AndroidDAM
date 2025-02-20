package com.example.simarropopandroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://4.212.9.198:8080/SimarroPopAccesoADatos/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val usuarioApi: UsuarioApi by lazy {
        retrofit.create(UsuarioApi::class.java)
    }
}
