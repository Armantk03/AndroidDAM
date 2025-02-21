package com.example.simarropopandroid.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.simarropopandroid.databinding.FragmentAgregarProductoBinding
import com.example.simarropopandroid.modelos.Categoria
import com.example.simarropopandroid.modelos.Foto
import com.example.simarropopandroid.modelos.Producto
import com.example.simarropopandroid.network.RetrofitClient
import com.example.simarropopandroid.modelos.UsuarioReferencia
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID


class AgregarProductoFragment : Fragment() {

    private var _binding: FragmentAgregarProductoBinding? = null
    private val binding get() = _binding!!
    private var categoriasList: List<Categoria> = emptyList()
    private var imageUri: Uri? = null

    private val seleccionarImagen = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
            binding.ivVistaPreviaImagen.setImageURI(it)
            binding.ivVistaPreviaImagen.visibility = View.VISIBLE
        }
    }

    private fun guardarFoto(idProducto: Int) {
        val storageReference = Firebase.storage.reference
        val nombreArchivo = "productos/${UUID.randomUUID()}.jpg" // 🔄 Nombre único para cada imagen
        val fotoRef = storageReference.child(nombreArchivo)

        imageUri?.let { uri ->
            val uploadTask = fotoRef.putFile(uri)
            uploadTask.addOnSuccessListener {
                // ✅ Imagen subida, obtenemos la URL
                fotoRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val fotoUrl = downloadUri.toString()
                    val foto = Foto(
                        url = fotoUrl,
                        descripcio = "Imagen del producto"
                    )

                    // 🌐 Guardar la URL en la base de datos mediante el endpoint
                    RetrofitClient.instance.registrarFoto(idProducto, foto).enqueue(object : Callback<Foto> {
                        override fun onResponse(call: Call<Foto>, response: Response<Foto>) {
                            if (response.isSuccessful) {
                                Toast.makeText(requireContext(), "Producto y foto subidos exitosamente ✅", Toast.LENGTH_SHORT).show()
                                requireActivity().supportFragmentManager.popBackStack()
                            } else {
                                Toast.makeText(requireContext(), " Error al guardar la foto en la API", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Foto>, t: Throwable) {
                            Toast.makeText(requireContext(), " Error al guardar foto: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "️ Error al subir la imagen a Firebase", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cargarCategorias()

        binding.btnSeleccionarImagen.setOnClickListener {
            seleccionarImagen.launch("image/*")
        }

        binding.btnGuardarProducto.setOnClickListener {
            guardarProducto()
        }
    }

    private fun cargarCategorias() {
        RetrofitClient.instance.obtenerCategorias().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful) {
                    categoriasList = response.body() ?: emptyList()
                    val categoriasNombres = categoriasList.map { it.nombre }
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoriasNombres)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerCategoria.adapter = adapter
                } else {
                    Toast.makeText(requireContext(), "Error al cargar categorías", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun guardarProducto() {
        val nombre = binding.etNombreProducto.text.toString()
        val descripcion = binding.etDescripcionProducto.text.toString()
        val precio = binding.etPrecioProducto.text.toString().toLongOrNull() ?: 0L
        val antiguedad = binding.etAntiguedadProducto.text.toString().toLongOrNull() ?: 0L
        val ubicacion = binding.etUbicacionProducto.text.toString()
        val categoriaSeleccionada = categoriasList[binding.spinnerCategoria.selectedItemPosition]

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && ubicacion.isNotEmpty() && imageUri != null) {
            val idUsuario = obtenerIdUsuario()
            val producto = Producto(
                id = null,
                nombre = nombre,
                descripcion = descripcion,
                antiguedad = antiguedad,
                precio = precio,
                ubicacion = ubicacion,
                deseado = false,
                usuario = UsuarioReferencia(id = obtenerIdUsuario()),  // 🔄 Nuevo modelo
                categoria = categoriaSeleccionada
            )


            RetrofitClient.instance.registrarProducto(producto).enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful) {
                        val productoGuardado = response.body()
                        productoGuardado?.id?.let { idProducto ->
                            guardarFoto(idProducto)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error al guardar producto: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(requireContext(), "Completa todos los campos y selecciona una imagen", Toast.LENGTH_SHORT).show()
        }
    }



    private fun obtenerIdUsuario(): Int {
        val sharedPreferences = requireActivity().getSharedPreferences("UserSession", android.content.Context.MODE_PRIVATE)
        return sharedPreferences.getInt("userId", -1) // -1 si no hay valor
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
