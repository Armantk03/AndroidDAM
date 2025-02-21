package com.example.simarropopandroid.modelos

data class Producto(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String,
    val antiguedad: Long,
    val precio: Long,
    val ubicacion: String,
    val deseado: Boolean,
    val usuario: UsuarioReferencia,  // 🔄 Cambiado a UsuarioReferencia
    val categoria: Categoria?,
    val imagenUrl: String? = null
)
