package com.sebastianvargas.geopin.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebastianvargas.geopin.databinding.FragmentDashboardBinding
import com.sebastianvargas.geopin.db.DatabaseHelper
import com.sebastianvargas.geopin.db.LocationEntity

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Instanciar el ViewModel usando el delegado "by viewModels"
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar el RecyclerView
        binding.recyclerViewLocations.layoutManager = LinearLayoutManager(requireContext())

        // Observar los cambios en las ubicaciones del ViewModel
        dashboardViewModel.locations.observe(viewLifecycleOwner, Observer { locations ->
            if (locations != null) {
                binding.recyclerViewLocations.adapter = LocationAdapter(locations, requireContext())
            }
        })

        // Cargar ubicaciones desde la base de datos
        loadLocationsFromDatabase()

        return root
    }

    private fun loadLocationsFromDatabase() {
        val dbHelper = DatabaseHelper(requireContext())
        val locations: List<LocationEntity> = dbHelper.getAllLocations()

        // Actualizar las ubicaciones en el ViewModel
        dashboardViewModel.loadLocations(locations)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
