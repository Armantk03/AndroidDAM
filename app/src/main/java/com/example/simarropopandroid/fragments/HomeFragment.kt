package com.example.simarropopandroid.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.simarropopandroid.R
import com.example.simarropopandroid.adapters.ProductoAdapter
import com.example.simarropopandroid.databinding.FragmentHomeBinding
import com.example.simarropopandroid.modelos.Categoria
import com.example.simarropopandroid.modelos.Producto
import com.example.simarropopandroid.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var listaProductos: List<Producto> = emptyList()
    private var listaCategorias: List<Categoria> = emptyList()
    private var categoriaSeleccionada: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        cargarCategorias()
        cargarProductos()

        binding.fabAgregarProducto.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, AgregarProductoFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun cargarCategorias() {
        RetrofitClient.instance.obtenerCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful) {
                    listaCategorias = response.body() ?: emptyList()
                    mostrarBotonesCategorias()
                } else {
                    Toast.makeText(requireContext(), "Error al obtener categorías", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de conexión al cargar categorías", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarBotonesCategorias() {
        val container = binding.categoriasContainer
        container.removeAllViews()

        val botonTodos = crearBotonCategoria("Todos")
        botonTodos.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
        categoriaSeleccionada = botonTodos
        botonTodos.setOnClickListener {
            resaltarCategoriaSeleccionada(botonTodos)
            mostrarProductosFiltrados(null)
        }
        container.addView(botonTodos)

        listaCategorias.forEach { categoria ->
            val botonCategoria = crearBotonCategoria(categoria.nombre)
            botonCategoria.setOnClickListener {
                resaltarCategoriaSeleccionada(botonCategoria)
                mostrarProductosFiltrados(categoria.id)
            }
            container.addView(botonCategoria)
        }
    }

    private fun crearBotonCategoria(nombre: String): Button {
        return Button(requireContext()).apply {
            text = nombre
            setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
            setTextColor(Color.WHITE)
            textSize = 14f
            stateListAnimator = null
            setPadding(20, 10, 20, 10)
            elevation = 10f
        }
    }

    private fun resaltarCategoriaSeleccionada(botonSeleccionado: Button) {
        categoriaSeleccionada?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
        botonSeleccionado.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
        categoriaSeleccionada = botonSeleccionado
    }

    private fun cargarProductos() {
        RetrofitClient.instance.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    listaProductos = response.body() ?: emptyList()
                    mostrarProductosFiltrados(null)
                } else {
                    Toast.makeText(requireContext(), "Error al obtener productos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun mostrarProductosFiltrados(categoriaId: Int?) {
        val productosFiltrados = if (categoriaId != null) {
            listaProductos.filter { it.categoria?.id == categoriaId }
        } else {
            listaProductos
        }

        if (productosFiltrados.isEmpty()) {
            Toast.makeText(requireContext(), "No hay productos en esta categoría", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = ProductoAdapter(productosFiltrados, requireActivity().supportFragmentManager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
