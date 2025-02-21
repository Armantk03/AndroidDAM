package com.example.simarropopandroid.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.simarropopandroid.LoginActivity
import com.example.simarropopandroid.databinding.FragmentUsuarioBinding
import com.example.simarropopandroid.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioFragment : Fragment() {

    private var _binding: FragmentUsuarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var usuarioRepository: UsuarioRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        usuarioRepository = UsuarioRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("UserSession", android.content.Context.MODE_PRIVATE)

        cargarDatosUsuario()

        binding.btnLogout.setOnClickListener {
            binding.btnLogout.isEnabled = false
            binding.logoutAnimation.visibility = View.VISIBLE
            binding.logoutAnimation.playAnimation()
            binding.logoutAnimation.addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    cerrarSesion()
                }
            })
        }

        binding.cardHacersePremium.setOnClickListener {
            mostrarAlertaPremium()
        }
    }

    // 🔄 Cargar los datos del usuario y su valoración
    private fun cargarDatosUsuario() {
        val userId = sharedPreferences.getInt("userId", -1)
        if (userId != -1) {
            CoroutineScope(Dispatchers.Main).launch {
                val usuario = usuarioRepository.obtenerUsuarioPorId(userId)
                usuario?.let {
                    binding.nombreUsuario.text = it.nombre
                    cargarValoracionUsuario(userId)
                } ?: run {
                    Toast.makeText(requireContext(), "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarValoracionUsuario(userId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val valoracion = usuarioRepository.obtenerValoracionUsuario(userId)
            Log.d("USUARIO_FRAGMENT", "Valoración obtenida: $valoracion")
            binding.userRating.rating = valoracion
            Toast.makeText(requireContext(), "Valoración obtenida: $valoracion", Toast.LENGTH_SHORT).show()
        }
    }


    // 💳 Mostrar alerta para hacerse premium
    private fun mostrarAlertaPremium() {
        AlertDialog.Builder(requireContext())
            .setTitle("Hacerse Premium")
            .setMessage("Al ser premium pagarás 10€/mes. ¿Deseas continuar?")
            .setPositiveButton("Aceptar") { _, _ -> actualizarPremiumConValoracion() }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    // 🌟 Actualizar el estado premium usando el endpoint de valoración
    private fun actualizarPremiumConValoracion() {
        val userId = sharedPreferences.getInt("userId", -1)
        if (userId != -1) {
            CoroutineScope(Dispatchers.Main).launch {
                val valoracion = usuarioRepository.obtenerValoracionUsuario(userId)
                if (valoracion >= 4.0) {
                    val actualizado = usuarioRepository.actualizarPremium(userId, true)
                    if (actualizado) {
                        Toast.makeText(requireContext(), "¡Ahora eres usuario premium!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Error al actualizar a premium", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Necesitas una valoración mínima de 4⭐ para ser premium.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 🚪 Cerrar sesión
    private fun cerrarSesion() {
        sharedPreferences.edit().clear().apply()
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
