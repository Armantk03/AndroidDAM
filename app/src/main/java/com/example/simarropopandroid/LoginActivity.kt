package com.example.simarropopandroid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.simarropopandroid.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)

        // Si el usuario ya está autenticado, redirigir a MainActivity
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            goToMainActivity()
        }

        // Manejar el botón de inicio de sesión
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
                goToMainActivity()
            } else {
                binding.tvErrorMessage.text = "Por favor, completa todos los campos"
                binding.tvErrorMessage.visibility = View.VISIBLE
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Cierra LoginActivity para que no pueda volver atrás
    }
}
