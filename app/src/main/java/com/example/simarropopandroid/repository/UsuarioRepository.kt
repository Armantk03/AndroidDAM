package com.example.simarropopandroid.repository

import android.content.Context
import com.example.simarropopandroid.database.AppDatabase
import com.example.simarropopandroid.modelos.UsuarioApi
import com.example.simarropopandroid.modelos.UsuarioEntity
import com.example.simarropopandroid.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class UsuarioRepository(context: Context) {
    private val usuarioDao = AppDatabase.getDatabase(context).usuarioDao()
    private val usuarioApi = RetrofitClient.usuarioApi

    suspend fun obtenerUsuarios(): List<UsuarioEntity> {
        return withContext(Dispatchers.IO) {
            try {
                val response = usuarioApi.obtenerUsuarios().awaitResponse()
                if (response.isSuccessful) {
                    response.body()?.let { usuarios ->
                        val usuariosEntity = usuarios.map { it.toUsuarioEntity() }
                        usuarioDao.insertarUsuarios(usuariosEntity)
                        return@withContext usuariosEntity
                    }
                }
                usuarioDao.obtenerUsuarios()
            } catch (e: Exception) {
                usuarioDao.obtenerUsuarios()
            }
        }
    }

    suspend fun iniciarSesion(correo: String, contrasenya: String): UsuarioEntity? {
        return withContext(Dispatchers.IO) {
            usuarioDao.iniciarSesion(correo, contrasenya)
        }
    }
}

// Función de conversión
fun UsuarioApi.toUsuarioEntity() = UsuarioEntity(
    id = id,
    nombre = "",
    correo = "",
    contrasenya = "",
    numTelefono = "",
    premium = false
)
