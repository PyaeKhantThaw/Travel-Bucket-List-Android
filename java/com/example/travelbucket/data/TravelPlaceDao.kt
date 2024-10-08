package com.example.travelbucket.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TravelPlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(travelPlace: TravelPlace)

    @Delete
    suspend fun delete(travelPlace: TravelPlace)

    @Query("SELECT * FROM travel_place_table WHERE _id = :id")
    suspend fun getTravelPlaceById(id: String): TravelPlace?


    @Query("SELECT * FROM travel_place_table")
    fun getAllTravelPlaces(): LiveData<List<TravelPlace>>
}
