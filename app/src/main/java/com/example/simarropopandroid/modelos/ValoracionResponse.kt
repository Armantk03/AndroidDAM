package com.example.simarropopandroid.modelos

data class ValoracionResponse(
    val id: Int,
    val valoracion: Int,
    val comentario: String,
    val usuario: UsuarioApi
)
