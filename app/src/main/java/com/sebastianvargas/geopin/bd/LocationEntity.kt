package com.sebastianvargas.geopin.db

data class LocationEntity(
    val id: Int = 0,
    val title: String,
    val description: String,
    val latitude: Double,
    val longitude: Double
)
