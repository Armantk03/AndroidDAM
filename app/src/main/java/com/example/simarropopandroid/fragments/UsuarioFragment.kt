package com.example.simarropopandroid.fragments


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.simarropopandroid.LoginActivity
import com.example.simarropopandroid.databinding.FragmentUsuarioBinding

class UsuarioFragment : Fragment() {

    private var _binding: FragmentUsuarioBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserSession", android.content.Context.MODE_PRIVATE)

        // Manejar el clic en el botón de cerrar sesión
        binding.btnLogout.setOnClickListener {
            // Desactivar el botón para evitar múltiples pulsaciones
            binding.btnLogout.isEnabled = false

            // Mostrar y reproducir la animación
            binding.logoutAnimation.visibility = View.VISIBLE
            binding.logoutAnimation.playAnimation()

            // Manejar el final de la animación
            binding.logoutAnimation.addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    cerrarSesion() // Cerrar la sesión después de la animación
                }
            })
        }
    }

    private fun cerrarSesion() {
        // Eliminar los datos de sesión
        sharedPreferences.edit().clear().apply()

        // Redirigir a LoginActivity
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)

        // Cerrar MainActivity para evitar volver atrás con el botón de "atrás"
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

