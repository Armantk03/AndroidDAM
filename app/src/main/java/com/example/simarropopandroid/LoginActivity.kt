package com.example.simarropopandroid

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.simarropopandroid.databinding.ActivityLoginBinding
import com.example.simarropopandroid.fragments.RegistroFragment
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

        // 🔑 Iniciar sesión
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

        // 📝 Navegar al RegistroFragment
        binding.tvRegister.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, RegistroFragment())
                .addToBackStack(null)
                .commit()
        }
    }
    private fun iniciarSesion(correo: String, contrasenya: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val usuario = usuarioRepository.iniciarSesionDesdeApi(correo, contrasenya)
            if (usuario != null) {
                sharedPreferences.edit().apply {
                    putBoolean("isLoggedIn", true)
                    putInt("userId", usuario.id ?: -1)
                    putString("userEmail", usuario.correo ?: "")
                    apply()  // 💾 ¡Importante para guardar los datos!
                }

                // 🕵️ Comprobar si se guardó el correo
                val correoUsuario = sharedPreferences.getString("userEmail", null)
                Toast.makeText(this@LoginActivity, "Correo guardado: $correoUsuario", Toast.LENGTH_SHORT).show()

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
