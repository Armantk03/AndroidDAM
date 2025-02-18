package com.example.simarropopandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simarropopandroid.adapters.ProductoDeseadoAdapter
import com.example.simarropopandroid.databinding.FragmentDeseadosBinding
import com.example.simarropopandroid.modelos.Producto
import com.example.simarropopandroid.modelos.Usuario
import com.example.simarropopandroid.modelos.Categoria

class DeseadosFragment : Fragment() {

    private var _binding: FragmentDeseadosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeseadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // com.example.simarropopandroid.model.Usuario y Categoría de ejemplo (simulación de API)
        val usuarioEjemplo = Usuario(1, "Juan", "juan@example.com", "1234", "+34 600 123 456", false)
        val categoriaEjemplo = Categoria(1, "Electrónica", "Dispositivos y gadgets")

        // Lista de productos deseados con datos correctos
        val productos = listOf(
            Producto(1, "Bicicleta", "Bicicleta de segunda mano", 4, 120.0, "Valencia", true, usuarioEjemplo, categoriaEjemplo, "https://via.placeholder.com/150"),
            Producto(2, "Smartphone", "Teléfono con 128GB de almacenamiento", 2, 250.0, "Madrid", false, usuarioEjemplo, categoriaEjemplo, "https://via.placeholder.com/150"),
            Producto(3, "Cámara", "Cámara réflex profesional", 1, 300.0, "Barcelona", true, usuarioEjemplo, categoriaEjemplo, "https://via.placeholder.com/150")
        )

        // Filtrar solo los productos deseados
        val productosDeseados = productos.filter { it.deseado }

        // Configurar el RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ProductoDeseadoAdapter(productosDeseados)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
