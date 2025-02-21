package com.example.simarropopandroid.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simarropopandroid.R
import com.example.simarropopandroid.databinding.ItemProductoBinding
import com.example.simarropopandroid.fragments.DetalleProductoFragment
import com.example.simarropopandroid.modelos.Producto

class ProductoAdapter(
    private val productos: List<Producto>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: Producto, fragmentManager: FragmentManager) {
            binding.productName.text = producto.nombre
            binding.productPrice.text = "€${producto.precio}"

            // Cargar imagen con Glide, manejar null y errores
            Glide.with(binding.root.context)
                .load(producto.imagenUrl ?: "https://via.placeholder.com/150") // Imagen de prueba si es null
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.productImage)

            binding.root.setOnClickListener {
                val fragment = DetalleProductoFragment().apply {
                    arguments = Bundle().apply {
                        putString("nombre", producto.nombre)
                        putString("precio", producto.precio.toString())
                        putString("descripcion", producto.descripcion)
                        putString("imagenUrl", producto.imagenUrl)
                        putInt("idUsuario", producto.usuario.id) // 💡 ID del usuario
                    }
                }
                loadFragment(fragment, fragmentManager)
            }

        }

        private fun loadFragment(fragment: Fragment, fragmentManager: FragmentManager) {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productos[position], fragmentManager)
    }

    override fun getItemCount() = productos.size
}
