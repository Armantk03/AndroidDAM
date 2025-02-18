package com.example.simarropopandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simarropopandroid.R
import com.example.simarropopandroid.databinding.ItemProductoBinding
import com.example.simarropopandroid.modelos.Producto

class ProductoDeseadoAdapter(
    private val productos: List<Producto>
) : RecyclerView.Adapter<ProductoDeseadoAdapter.ProductoViewHolder>() {

    class ProductoViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: Producto) {
            binding.productName.text = producto.nombre
            binding.productPrice.text = "€${producto.precio}"

            // Cargar imagen desde la URL con Glide
            Glide.with(binding.root.context)
                .load(producto.imagenUrl ?: "https://via.placeholder.com/150") // Imagen de prueba si es null
                .placeholder(R.drawable.ic_placeholder) // Imagen temporal mientras carga
                .into(binding.productImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productos[position])
    }

    override fun getItemCount() = productos.size
}
