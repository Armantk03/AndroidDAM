import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.simarropopandroid.databinding.FragmentDetalleProductoBinding
import com.example.simarropopandroid.repository.UsuarioRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.location.Geocoder
import com.example.simarropopandroid.R
import java.util.*

class DetalleProductoFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDetalleProductoBinding? = null
    private val binding get() = _binding!!
    private lateinit var usuarioRepository: UsuarioRepository
    private lateinit var mMap: GoogleMap
    private var ubicacion: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleProductoBinding.inflate(inflater, container, false)
        usuarioRepository = UsuarioRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nombre = arguments?.getString("nombre")
        val precio = arguments?.getString("precio")
        val descripcion = arguments?.getString("descripcion")
        val imagenUrl = arguments?.getString("imagenUrl")
        ubicacion = arguments?.getString("ubicacion")

        Log.d("DETALLE_PRODUCTO", "Ubicación recibida: $ubicacion")  // ✅ Verifica ubicación recibida

        binding.productName.text = nombre
        binding.productPrice.text = "€$precio"
        binding.productDescription.text = descripcion

        imagenUrl?.let {
            Glide.with(this).load(it).into(binding.productImage)
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mostrarUbicacionEnMapa()
    }

    private fun mostrarUbicacionEnMapa() {
        ubicacion?.let { direccion ->
            Log.d("DETALLE_PRODUCTO", "Buscando coordenadas para: $direccion")  // ✅ Verificar dirección
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val resultado = geocoder.getFromLocationName(direccion, 1)
                    if (resultado?.isNotEmpty() == true) {
                        val ubicacionLatLng = LatLng(resultado[0].latitude, resultado[0].longitude)
                        Log.d("DETALLE_PRODUCTO", "Coordenadas obtenidas: $ubicacionLatLng")  // ✅ Coordenadas obtenidas
                        CoroutineScope(Dispatchers.Main).launch {
                            mMap.addMarker(MarkerOptions().position(ubicacionLatLng).title("Ubicación del producto"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng, 15f))
                        }
                    } else {
                        Log.e("DETALLE_PRODUCTO", "❌ No se encontraron coordenadas para: $direccion")
                    }
                } catch (e: Exception) {
                    Log.e("DETALLE_PRODUCTO", "🚨 Error al obtener ubicación: ${e.message}")
                    e.printStackTrace()
                }
            }
        } ?: Log.e("DETALLE_PRODUCTO", "❌ Ubicación es null")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
