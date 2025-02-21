package com.example.simarropopandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.simarropopandroid.databinding.FragmentDetalleProductoBinding
import com.example.simarropopandroid.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleProductoFragment : Fragment() {

    private var _binding: FragmentDetalleProductoBinding? = null
    private val binding get() = _binding!!
    private var isLiked = false
    private var idProducto: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombre = arguments?.getString("nombre")
        val precio = arguments?.getString("precio")
        val descripcion = arguments?.getString("descripcion")
        val imagenUrl = arguments?.getString("imagenUrl")
        idProducto = arguments?.getInt("idProducto") ?: -1

        binding.productName.text = nombre
        binding.productPrice.text = "€$precio"
        binding.productDescription.text = descripcion

        imagenUrl?.let {
            Glide.with(this).load(it).into(binding.productImage)
        }

        // ⭐ Cuando el usuario pulse el botón "Me gusta"
        binding.btnLike.setOnClickListener {
            isLiked = !isLiked
            animarBotonLike(isLiked)
            actualizarDeseado(idProducto, isLiked)
        }
    }

    private fun animarBotonLike(like: Boolean) {
        if (like) {
            binding.btnLike.setMinAndMaxProgress(0f, 1f)
            binding.btnLike.playAnimation()
        } else {
            binding.btnLike.cancelAnimation()
            binding.btnLike.progress = 0f
        }
    }

    private fun actualizarDeseado(idProducto: Int, esDeseado: Boolean) {
        RetrofitClient.instance.actualizarDeseado(idProducto, esDeseado)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        val mensaje = if (esDeseado) "💚 Producto añadido a deseados" else "💔 Producto eliminado de deseados"
                        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "❌ Error al actualizar estado: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(requireContext(), "🚨 Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
