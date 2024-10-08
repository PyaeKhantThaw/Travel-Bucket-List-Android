package com.example.travelbucket.network

import com.example.travelbucket.data.TravelPlace
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TravelApiService {
    @GET("travelplaces")
    fun getTravelPlaces(): Call<List<TravelPlace>>

    @GET("travelplaces/{id}")
    fun getTravelPlaceById(@Path("id") id: String): Call<TravelPlace>



}
