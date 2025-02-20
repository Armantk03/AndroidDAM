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
import com.example.simarropopandroid.modelos.UsuarioApi
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

        // ✅ Usamos UsuarioApi, que solo contiene el ID
        val usuarioEjemplo = UsuarioApi(id = 1)
        val categoriaEjemplo = Categoria(id = 1, nombre = "Electrónica", descripcion = "Dispositivos y gadgets")

        // ✅ Lista de productos deseados (simulación de API)
        val productos = listOf(
            Producto(id = 1, nombre = "Bicicleta", descripcion = "Bicicleta de segunda mano", antiguedad = 4, precio = 120, ubicacion = "Valencia", deseado = true, usuario = usuarioEjemplo, categoria = categoriaEjemplo, imagenUrl = "https://via.placeholder.com/150"),
            Producto(id = 2, nombre = "Smartphone", descripcion = "Teléfono con 128GB de almacenamiento", antiguedad = 2, precio = 250, ubicacion = "Madrid", deseado = false, usuario = usuarioEjemplo, categoria = categoriaEjemplo, imagenUrl = "https://via.placeholder.com/150"),
            Producto(id = 3, nombre = "Cámara", descripcion = "Cámara réflex profesional", antiguedad = 1, precio = 300, ubicacion = "Barcelona", deseado = true, usuario = usuarioEjemplo, categoria = categoriaEjemplo, imagenUrl = "https://via.placeholder.com/150")
        )

        // ✅ Filtrar solo los productos deseados
        val productosDeseados = productos.filter { it.deseado }

        // ✅ Configurar el RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ProductoDeseadoAdapter(productosDeseados)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
