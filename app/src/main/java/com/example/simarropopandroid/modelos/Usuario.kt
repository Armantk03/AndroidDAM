package com.example.simarropopandroid.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val id: Int,
    val nombre: String,
    val correo: String,
    val contrasenya: String,
    val numTelefono: String,
    val premium: Boolean
)
