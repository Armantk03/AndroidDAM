package com.example.simarropopandroid.modelos

import androidx.room.Entity

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val antiguedad: Int,
    val precio: Double,
    val ubicacion: String,
    val deseado: Boolean,
    val usuario: Usuario,
    val categoria: Categoria,
    val imagenUrl: String? = null // Campo opcional para manejar imágenes
)

@Entity
data class Usuario(
    val id: Int,
    val nombre: String,
    val correo: String,
    val contrasenya: String,
    val numTelefono: String,
    val premium: Boolean
)

data class Categoria(
    val id: Int,
    val nombre: String,
    val descripcion: String
)
