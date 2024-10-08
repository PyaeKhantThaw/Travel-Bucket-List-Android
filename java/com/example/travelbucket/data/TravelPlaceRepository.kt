package com.example.travelbucket.data

import androidx.lifecycle.LiveData

class TravelPlaceRepository(private val travelPlaceDao: TravelPlaceDao) {

    val allTravelPlaces: LiveData<List<TravelPlace>> = travelPlaceDao.getAllTravelPlaces()

    suspend fun insert(travelPlace: TravelPlace) {
        travelPlaceDao.insert(travelPlace)
    }

    suspend fun delete(travelPlace: TravelPlace) {
        travelPlaceDao.delete(travelPlace)
    }

    suspend fun getTravelPlaceById(id: String): TravelPlace? {
        return travelPlaceDao.getTravelPlaceById(id)
    }
}
