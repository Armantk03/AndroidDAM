package com.example.simarropopandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.simarropopandroid.databinding.FragmentDetalleProductoBinding
import com.example.simarropopandroid.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log

class DetalleProductoFragment : Fragment() {

    private var _binding: FragmentDetalleProductoBinding? = null
    private val binding get() = _binding!!
    private lateinit var usuarioRepository: UsuarioRepository
    private var isLiked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleProductoBinding.inflate(inflater, container, false)
        usuarioRepository = UsuarioRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombre = arguments?.getString("nombre")
        val precio = arguments?.getString("precio")
        val descripcion = arguments?.getString("descripcion")
        val imagenUrl = arguments?.getString("imagenUrl")
        val idUsuario = arguments?.getInt("idUsuario") ?: -1

        // Mostrar datos del producto
        binding.productName.text = nombre
        binding.productPrice.text = "€$precio"
        binding.productDescription.text = descripcion

        imagenUrl?.let {
            Glide.with(this).load(it).into(binding.productImage)
        }

        // Obtener el nombre y valoración del usuario desde la API por su ID
        if (idUsuario != -1) {
            obtenerNombreUsuario(idUsuario)
            cargarValoracionUsuario(idUsuario)
        }

        // Animación "Me gusta"
        binding.btnLike.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                binding.btnLike.setMinAndMaxProgress(0f, 1f)
                binding.btnLike.playAnimation()
            } else {
                binding.btnLike.cancelAnimation()
                binding.btnLike.progress = 0f
            }
        }
    }

    private fun obtenerNombreUsuario(idUsuario: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val usuario = usuarioRepository.obtenerUsuarioPorId(idUsuario)
            usuario?.let {
                binding.nombreUsuario.text = it.nombre // Mostrar nombre del usuario que subió el producto
            } ?: run {
                Toast.makeText(requireContext(), "No se pudo cargar el nombre del usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarValoracionUsuario(idUsuario: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val valoracion = usuarioRepository.obtenerValoracionUsuario(idUsuario)
            Log.d("DETALLE_PRODUCTO", "Valoración obtenida para userId $idUsuario: $valoracion ⭐")
            if (valoracion > 0f) {
                binding.userRating.rating = valoracion
            } else {
                Toast.makeText(requireContext(), "No se pudo obtener la valoración", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
