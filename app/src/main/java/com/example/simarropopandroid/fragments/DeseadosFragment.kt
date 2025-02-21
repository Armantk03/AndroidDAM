import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simarropopandroid.adapters.ProductoDeseadoAdapter
import com.example.simarropopandroid.databinding.FragmentDeseadosBinding
import com.example.simarropopandroid.modelos.Producto
import com.example.simarropopandroid.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeseadosFragment : Fragment() {

    private var _binding: FragmentDeseadosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeseadosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        cargarProductosDeseados()
    }

    private fun cargarProductosDeseados() {
        RetrofitClient.instance.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productosDeseados = response.body()?.filter { it.deseado } ?: emptyList()
                    binding.recyclerView.adapter = ProductoDeseadoAdapter(productosDeseados)
                } else {
                    Toast.makeText(requireContext(), " Error al cargar productos deseados", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(requireContext(), " Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
