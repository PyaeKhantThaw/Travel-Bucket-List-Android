package com.example.travelbucket.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.travelbucket.data.TravelPlaceRepository

class TravelPlaceViewModelFactory(
    private val repository: TravelPlaceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TravelPlaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TravelPlaceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
