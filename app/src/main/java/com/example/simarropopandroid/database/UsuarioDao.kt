package com.example.simarropopandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simarropopandroid.modelos.Usuario

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuarios(usuarios: List<Usuario>)

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasenya = :contrasenya LIMIT 1")
    suspend fun iniciarSesion(correo: String, contrasenya: String): Usuario?

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerUsuarios(): List<Usuario>
}
