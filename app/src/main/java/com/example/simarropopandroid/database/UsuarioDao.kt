package com.example.simarropopandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simarropopandroid.modelos.UsuarioEntity

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuarios(usuarios: List<UsuarioEntity>)

    @Query("SELECT * FROM usuarios WHERE TRIM(LOWER(correo)) = TRIM(LOWER(:correo)) AND TRIM(contrasenya) = TRIM(:contrasenya) LIMIT 1")
    suspend fun iniciarSesion(correo: String, contrasenya: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerUsuarios(): List<UsuarioEntity>
}
