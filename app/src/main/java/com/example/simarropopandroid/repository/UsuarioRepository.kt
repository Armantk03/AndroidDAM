package com.example.simarropopandroid.repository

import android.content.Context
import android.util.Log
import com.example.simarropopandroid.modelos.UsuarioApi
import com.example.simarropopandroid.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class UsuarioRepository(context: Context) {
    private val usuarioApi = RetrofitClient.usuarioApi

    // 🔐 Validar credenciales desde la API
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

    // 🔄 Obtener usuarios
    suspend fun obtenerUsuarios(): List<UsuarioApi> {
        return withContext(Dispatchers.IO) {
            val response = usuarioApi.obtenerUsuarios().awaitResponse()
            if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
        }
    }

    suspend fun registrarUsuario(usuario: UsuarioApi): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = usuarioApi.registrarUsuario(usuario).awaitResponse()
                if (!response.isSuccessful) {
                    println("❌ Error al registrar: Código ${response.code()} - ${response.errorBody()?.string()}")
                }
                response.isSuccessful
            } catch (e: Exception) {
                println("🚨 Excepción al registrar usuario: ${e.message}")
                false
            }
        }
    }

    suspend fun actualizarPremium(userId: Int, esPremium: Boolean): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val usuario = usuarioApi.listarPorId(userId).awaitResponse().body()
                if (usuario != null) {
                    val usuarioActualizado = usuario.copy(premium = esPremium)
                    val response = usuarioApi.modificarUsuario(userId, usuarioActualizado).awaitResponse()
                    response.isSuccessful
                } else false
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    suspend fun obtenerUsuarioPorId(userId: Int): UsuarioApi? {
        return withContext(Dispatchers.IO) {
            try {
                val response = usuarioApi.listarPorId(userId).awaitResponse()
                if (response.isSuccessful) response.body() else null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }


    suspend fun obtenerValoracionUsuario(userId: Int): Float {
        return withContext(Dispatchers.IO) {
            try {
                val response = usuarioApi.obtenerValoracion(userId).awaitResponse()
                if (response.isSuccessful) {
                    val valoraciones = response.body() ?: emptyList()
                    if (valoraciones.isNotEmpty()) {
                        valoraciones[0].valoracion.toFloat()
                    } else 0f
                } else 0f
            } catch (e: Exception) {
                e.printStackTrace()
                0f
            }
        }
    }






    suspend fun hacerUsuarioPremium(correo: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = usuarioApi.hacerPremium(correo).awaitResponse()
                if (!response.isSuccessful) {
                    println("❌ Error al actualizar a premium: Código ${response.code()} - ${response.errorBody()?.string()}")
                }
                response.isSuccessful
            } catch (e: Exception) {
                println("🚨 Excepción al actualizar premium: ${e.message}")
                false
            }
        }
    }





}
