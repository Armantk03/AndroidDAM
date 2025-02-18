package com.example.simarropopandroid.repository

import android.content.Context
import com.example.simarropopandroid.database.AppDatabase
import com.example.simarropopandroid.modelos.Usuario
import com.example.simarropopandroid.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class UsuarioRepository(context: Context) {
    private val usuarioDao = AppDatabase.getDatabase(context).usuarioDao()
    private val usuarioApi = RetrofitClient.getUsuarioApi()

    suspend fun obtenerUsuarios(): List<Usuario> {
        return withContext(Dispatchers.IO) {
            try {
                val response = usuarioApi.obtenerUsuarios().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { usuarios ->
                        usuarioDao.insertarUsuarios(usuarios)
                        return@withContext usuarios
                    }
                }
                usuarioDao.obtenerUsuarios() // Si falla, usar los locales
            } catch (e: Exception) {
                usuarioDao.obtenerUsuarios() // Si hay error, usar Room
            }
        }
    }

    suspend fun iniciarSesion(correo: String, contrasenya: String): Usuario? {
        return withContext(Dispatchers.IO) {
            usuarioDao.iniciarSesion(correo, contrasenya)
        }
    }
}
