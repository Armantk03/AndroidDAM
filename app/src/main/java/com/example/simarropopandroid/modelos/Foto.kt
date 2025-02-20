package com.example.simarropopandroid.modelos

data class Foto(
    val id: Int? = null,
    val url: String,
    val descripcio: String,
    val producto: Producto? = null // Hacemos que sea opcional

)
