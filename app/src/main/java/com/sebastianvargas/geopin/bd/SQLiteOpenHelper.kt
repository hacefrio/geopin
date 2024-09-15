package com.sebastianvargas.geopin.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

// Definir constantes para la base de datos
private const val DATABASE_NAME = "GeoPinDB"
private const val DATABASE_VERSION = 1
private const val TABLE_LOCATIONS = "locations"
private const val KEY_ID = "id"
private const val KEY_TITLE = "title"
private const val KEY_DESCRIPTION = "description"
private const val KEY_LATITUDE = "latitude"
private const val KEY_LONGITUDE = "longitude"

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de ubicaciones
        val createTable = ("CREATE TABLE $TABLE_LOCATIONS ("
                + "$KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$KEY_TITLE TEXT,"
                + "$KEY_DESCRIPTION TEXT,"
                + "$KEY_LATITUDE REAL,"
                + "$KEY_LONGITUDE REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Si la base de datos existe, eliminar y volver a crear
        db.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
        onCreate(db)
    }

    // Método para agregar una nueva ubicación
    fun addLocation(title: String, description: String, latitude: Double, longitude: Double): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, title)
        values.put(KEY_DESCRIPTION, description)
        values.put(KEY_LATITUDE, latitude)
        values.put(KEY_LONGITUDE, longitude)

        // Insertar fila
        val id = db.insert(TABLE_LOCATIONS, null, values)
        db.close() // Cerrar la base de datos
        return id
    }

    // Método para obtener todas las ubicaciones
    fun getAllLocations(): List<LocationEntity> {
        val locationList = ArrayList<LocationEntity>()
        val selectQuery = "SELECT * FROM $TABLE_LOCATIONS"

        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val location = LocationEntity(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                    title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DESCRIPTION)),
                    latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LATITUDE)),
                    longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LONGITUDE))
                )
                locationList.add(location)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return locationList
    }
}
