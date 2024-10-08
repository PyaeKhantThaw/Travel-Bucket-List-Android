package com.example.travelbucket.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [TravelPlace::class], version = 1)
@TypeConverters(Converters::class)
abstract class TravelPlaceDatabase : RoomDatabase() {

    abstract fun travelPlaceDao(): TravelPlaceDao

    companion object {
        @Volatile
        private var INSTANCE: TravelPlaceDatabase? = null

        fun getDatabase(context: Context): TravelPlaceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelPlaceDatabase::class.java,
                    "travel_place_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
