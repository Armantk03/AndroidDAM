package com.example.simarropopandroid.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

// Para la API

// ✅ Usuario para la API con todos los campos necesarios
data class UsuarioApi(
    val id: Int? = null,
    val nombre: String,
    val correo: String,
    val contrasenya: String,
    val numTelefono: String,
    val premium: Boolean
)


data class UsuarioReferencia(
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
