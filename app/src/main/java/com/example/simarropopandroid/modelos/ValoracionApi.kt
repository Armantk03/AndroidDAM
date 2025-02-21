import com.example.simarropopandroid.modelos.UsuarioApi

data class ValoracionApi(
    val id: Int,
    val valoracion: Int,
    val comentario: String,
    val usuario: UsuarioApi
)
