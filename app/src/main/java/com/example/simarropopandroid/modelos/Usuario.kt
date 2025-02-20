package com.example.simarropopandroid.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

// Para la API
data class UsuarioApi(
    val id: Int
)

// Para Room (persistencia local)
@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val correo: String,
    val contrasenya: String,
    val numTelefono: String,
    val premium: Boolean
)
