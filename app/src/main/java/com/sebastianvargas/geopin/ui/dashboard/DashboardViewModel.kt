package com.sebastianvargas.geopin.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sebastianvargas.geopin.db.LocationEntity

class DashboardViewModel : ViewModel() {

    // Datos en vivo para almacenar la lista de ubicaciones
    private val _locations = MutableLiveData<List<LocationEntity>>()
    val locations: LiveData<List<LocationEntity>> = _locations

    // Simular la carga de datos o recuperarlos desde una base de datos u otra fuente
    fun loadLocations(locationsFromDb: List<LocationEntity>) {
        _locations.value = locationsFromDb
    }
}
