package com.example.simarropopandroid.modelos

import com.google.gson.annotations.SerializedName

data class Categoria(
    val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String
)
