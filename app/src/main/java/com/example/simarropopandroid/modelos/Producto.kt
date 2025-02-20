package com.example.simarropopandroid.modelos

data class Producto(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String,
    val antiguedad: Long,
    val precio: Long,
    val ubicacion: String,
    val deseado: Boolean,
    val usuario: UsuarioApi, // Usar UsuarioApi en lugar de UsuarioEntity
    val categoria: Categoria,
    val imagenUrl: String? = null
)
