package com.example.simarropopandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.simarropopandroid.R
import com.example.simarropopandroid.adapters.ProductoAdapter
import com.example.simarropopandroid.databinding.FragmentHomeBinding
import com.example.simarropopandroid.modelos.Producto
import com.example.simarropopandroid.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔄 Mostrar productos en un GridLayout
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        cargarProductos()

        // ➕ Al pulsar el FAB, abrir AgregarProductoFragment
        binding.fabAgregarProducto.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, AgregarProductoFragment()) // Reemplaza el fragmento actual
                .addToBackStack(null) // Permite regresar al HomeFragment
                .commit()
        }
    }

    private fun cargarProductos() {
        RetrofitClient.instance.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productos = response.body() ?: emptyList()

                    if (productos.isNotEmpty()) {
                        binding.recyclerView.adapter = ProductoAdapter(productos, requireActivity().supportFragmentManager)
                    } else {
                        Toast.makeText(requireContext(), "No hay productos disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al obtener productos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
