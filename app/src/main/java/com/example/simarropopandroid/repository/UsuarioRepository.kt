package com.example.simarropopandroid.repository

import android.content.Context
import com.example.simarropopandroid.modelos.UsuarioApi
import com.example.simarropopandroid.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class UsuarioRepository(context: Context) {
    private val usuarioApi = RetrofitClient.usuarioApi

    // 🔍 Validar credenciales recorriendo todos los usuarios
    suspend fun iniciarSesionDesdeApi(correo: String, contrasenya: String): UsuarioApi? {
        return withContext(Dispatchers.IO) {
            try {
                val response = usuarioApi.obtenerUsuarios().awaitResponse()
                if (response.isSuccessful) {
                    val usuarios = response.body() ?: emptyList()
                    usuarios.find {
                        it.correo.trim() == correo.trim() && it.contrasenya.trim() == contrasenya.trim()
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun obtenerUsuarios(): List<UsuarioApi> {
        return withContext(Dispatchers.IO) {
            val response = usuarioApi.obtenerUsuarios().awaitResponse()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                emptyList()
            }
        }
    }

}
