package com.example.simarropopandroid.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import com.example.simarropopandroid.R
import com.example.simarropopandroid.databinding.FragmentDetalleProductoBinding

class DetalleProductoFragment : Fragment() {

    private var _binding: FragmentDetalleProductoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var isLiked = false // Estado inicial del botón "Me gusta"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombre = arguments?.getString("nombre")
        val precio = arguments?.getString("precio")
        val descripcion = arguments?.getString("descripcion")
        val imagenResId = arguments?.getInt("imagenResId")

        // Asignar datos a las vistas
        binding.productName.text = nombre
        binding.productPrice.text = "€$precio"
        binding.productDescription.text = descripcion
        imagenResId?.let { binding.productImage.setImageResource(it) }

        // Manejar el botón "Me gusta" con animación Lottie
        binding.btnLike.setOnClickListener {
            isLiked = !isLiked
            if (isLiked) {
                binding.btnLike.setMinAndMaxProgress(0f, 1f)
                binding.btnLike.playAnimation()
            } else {
                binding.btnLike.cancelAnimation() // Detener la animación
                binding.btnLike.progress = 0f // Volver al inicio
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
