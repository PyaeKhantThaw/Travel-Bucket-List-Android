package com.example.travelbucket.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbucket.data.TravelPlace
import com.example.travelbucket.data.TravelPlaceRepository
import kotlinx.coroutines.launch

class TravelPlaceViewModel(private val repository: TravelPlaceRepository) : ViewModel() {

    val allTravelPlaces: LiveData<List<TravelPlace>> = repository.allTravelPlaces

    fun insert(travelPlace: TravelPlace) = viewModelScope.launch {
        repository.insert(travelPlace)
    }

    fun delete(travelPlace: TravelPlace) = viewModelScope.launch {
        repository.delete(travelPlace)
    }

    fun getTravelPlaceById(id: String, callback: (TravelPlace?) -> Unit) = viewModelScope.launch {
        val travelPlace = repository.getTravelPlaceById(id)
        callback(travelPlace)
    }
}
