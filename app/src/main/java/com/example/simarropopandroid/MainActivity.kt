package com.example.simarropopandroid

import DeseadosFragment
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.simarropopandroid.databinding.ActivityMainBinding
import com.example.simarropopandroid.databinding.FragmentHomeBinding
import com.example.simarropopandroid.fragments.*
import com.example.simarropopandroid.repository.UsuarioRepository
import com.google.android.material.navigation.NavigationView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var _binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// 🔥 Probar la conexión a Firebase Storage
        val storage = Firebase.storage
        println("✅ Firebase Storage inicializado: $storage")
        // Inicializar ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Descargar y guardar usuarios en Room al iniciar
        CoroutineScope(Dispatchers.IO).launch {
            UsuarioRepository(applicationContext).obtenerUsuarios()
            Log.d("MAIN_ACTIVITY", "Usuarios descargados e insertados en Room")
        }

        // Configurar la Toolbar
        setSupportActionBar(binding.toolbar)

        // Configurar el Drawer Toggle
        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar el NavigationView
        binding.navigationView.setNavigationItemSelectedListener(this)

        // Cargar el fragmento inicial
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment()).commit()
            binding.navigationView.setCheckedItem(R.id.nav_home)
        }

        _binding.fabAgregarProducto.setOnClickListener {
            val fragment = AgregarProductoFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    // Manejar la selección de elementos en el NavigationView
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> replaceFragment(HomeFragment(), "Inicio")
            R.id.nav_usuario -> replaceFragment(UsuarioFragment(), "Usuario")
            R.id.nav_deseados -> replaceFragment(DeseadosFragment(), "Deseados")
            R.id.nav_settings -> replaceFragment(SettingsFragment(), "Ajustes") // ⚡ Nuevo fragmento de ajustes
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    // Reemplazar fragmentos
    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        supportActionBar?.title = title // Actualizar el título de la Toolbar
    }

    // Manejar el botón de retroceso para cerrar el menú lateral
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
