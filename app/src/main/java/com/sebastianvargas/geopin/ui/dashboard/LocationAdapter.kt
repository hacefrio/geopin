package com.sebastianvargas.geopin.ui.dashboard

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sebastianvargas.geopin.R
import com.sebastianvargas.geopin.db.LocationEntity

class LocationAdapter(private val locations: List<LocationEntity>, private val context: Context) :
    RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    class LocationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textViewTitle)
        val coordinates: TextView = view.findViewById(R.id.textViewCoordinates)
        val description: TextView = view.findViewById(R.id.textViewDescription)
        val buttonOpenInMaps: Button = view.findViewById(R.id.buttonOpenInMaps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.title.text = location.title
        holder.coordinates.text = "Lat: ${location.latitude}, Lon: ${location.longitude}"
        holder.description.text = location.description

        // Configurar el botón para abrir la ubicación en Google Maps
        holder.buttonOpenInMaps.setOnClickListener {
            try {


                // Verificar si Google Maps está instalado
                context.packageManager.getPackageInfo("com.google.android.apps.maps", PackageManager.GET_ACTIVITIES)

                // Si Google Maps está instalado, abrir la ubicación en Google Maps
                val gmmIntentUri = Uri.parse("geo:0,0?q=${location.latitude},${location.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context.startActivity(mapIntent)
            } catch (e: PackageManager.NameNotFoundException) {
                // Si Google Maps no está instalado, abrir la ubicación en el navegador
                val browserUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=${location.latitude},${location.longitude}")
                val browserIntent = Intent(Intent.ACTION_VIEW, browserUri)
                if (browserIntent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(browserIntent)
                } else {
                    Toast.makeText(context, "No hay aplicaciones de mapas instaladas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int = locations.size
}
