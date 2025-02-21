package com.example.simarropopandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.simarropopandroid.databinding.FragmentRegistroBinding
import com.example.simarropopandroid.modelos.UsuarioApi
import com.example.simarropopandroid.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistroFragment : Fragment() {

    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private lateinit var usuarioRepository: UsuarioRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        usuarioRepository = UsuarioRepository(requireContext())  // Inicializar repositorio
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().trim()
            val correo = binding.etCorreo.text.toString().trim()
            val contrasena = binding.etContrasena.text.toString().trim()
            val telefono = binding.etTelefono.text.toString().trim()

            if (nombre.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty() && telefono.isNotEmpty()) {
                val nuevoUsuario = UsuarioApi(
                    nombre = nombre,
                    correo = correo,
                    contrasenya = contrasena,
                    numTelefono = telefono,
                    premium = false
                )

                registrarUsuario(nuevoUsuario)
            } else {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registrarUsuario(usuario: UsuarioApi) {
        CoroutineScope(Dispatchers.Main).launch {
            val registrado = usuarioRepository.registrarUsuario(usuario)
            if (registrado) {
                Toast.makeText(requireContext(), "Registro exitoso, inicia sesión", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "❌ Error al registrar usuario. Revisa Logcat para más detalles.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
