package com.sebastianvargas.geopin.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [LocationEntity::class], version = 1)
abstract class GeoPinDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: GeoPinDatabase? = null

        fun getDatabase(context: Context): GeoPinDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =Room.databaseBuilder(
                    context.applicationContext,
                    GeoPinDatabase::class.java,
                    "geo_pin_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("GeoPinDatabase", "Database created!")
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

    }
}
