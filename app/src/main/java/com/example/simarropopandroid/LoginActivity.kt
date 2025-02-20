package com.example.simarropopandroid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.simarropopandroid.databinding.ActivityLoginBinding
import com.example.simarropopandroid.modelos.UsuarioEntity
import com.example.simarropopandroid.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var usuarioRepository: UsuarioRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        usuarioRepository = UsuarioRepository(applicationContext)

        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            goToMainActivity()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                iniciarSesion(email, password)
            } else {
                binding.tvErrorMessage.text = "Por favor, completa todos los campos"
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun iniciarSesion(correo: String, contrasenya: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val usuario: UsuarioEntity? = usuarioRepository.iniciarSesion(correo, contrasenya)
            if (usuario != null) {
                sharedPreferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .putInt("userId", usuario.id)
                    .apply()
                goToMainActivity()
            } else {
                binding.tvErrorMessage.text = "Correo o contraseña incorrectos"
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
