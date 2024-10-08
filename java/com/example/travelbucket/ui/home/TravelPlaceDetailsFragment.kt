package com.example.travelbucket.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.travelbucket.data.TravelPlace
import com.example.travelbucket.data.TravelPlaceDatabase
import com.example.travelbucket.data.TravelPlaceRepository
import com.example.travelbucket.databinding.FragmentTravelPlaceDetailsBinding
import com.example.travelbucket.network.RetrofitClient
import com.example.travelbucket.ui.home.TravelPlaceViewModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravelPlaceDetailsFragment : Fragment() {

    private var _binding: FragmentTravelPlaceDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var travelPlaceId: String
    private lateinit var travelPlaceViewModel: TravelPlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize the binding
        _binding = FragmentTravelPlaceDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve travelPlaceId from arguments
        travelPlaceId = arguments?.getString("travelPlaceId") ?: ""

        // Create the ViewModel using the custom factory
        val repository = TravelPlaceRepository(TravelPlaceDatabase.getDatabase(requireContext()).travelPlaceDao())
        val factory = TravelPlaceViewModelFactory(repository)
        travelPlaceViewModel = ViewModelProvider(this, factory).get(TravelPlaceViewModel::class.java)

        Log.d("TravelPlaceDetailsFragment", "travelPlaceId is $travelPlaceId")

        // Fetch travel place details
        fetchTravelPlaceDetails()

        // Check if the place is already in the bucket list
        travelPlaceViewModel.getTravelPlaceById(travelPlaceId) { travelPlace ->
            if (travelPlace != null) {
                binding.btnAddToBucket.text = "Added to Bucket List"
            } else {
                binding.btnAddToBucket.text = "Add to Bucket List"
            }
        }

        // Button click listener
        binding.btnAddToBucket.setOnClickListener {
            travelPlaceViewModel.getTravelPlaceById(travelPlaceId) { travelPlace ->
                if (travelPlace != null) {
                    travelPlaceViewModel.delete(travelPlace)
                    binding.btnAddToBucket.text = "Add to Bucket List"
                } else {
                    val estimatedCostString = binding.textEstimatedCost.text.toString()
                    val estimatedCost = estimatedCostString.replace("Estimated cost: $ ", "").toIntOrNull() ?: 0

                    val newPlace = TravelPlace(
                        _id = travelPlaceId,
                        city = binding.textCity.text.toString(),
                        imageUrls = listOf("image_url_here"), // Replace with actual image URL
                        description = binding.textDescription.text.toString(),
                        estimatedCost = estimatedCost,
                        bestSeasonToTravel = binding.textBestSeason.text.toString()
                    )
                    travelPlaceViewModel.insert(newPlace)
                    binding.btnAddToBucket.text = "Added to Bucket List"
                }
            }
        }






    }

    private fun fetchTravelPlaceDetails() {
        RetrofitClient.instance.getTravelPlaceById(travelPlaceId).enqueue(object : Callback<TravelPlace> {
            override fun onResponse(call: Call<TravelPlace>, response: Response<TravelPlace>) {
                if (response.isSuccessful) {
                    Log.d("TravelPlaceDetailsFragment", "The response from server is ${response.body()}")
                    val travelPlace = response.body()
                    travelPlace?.let {
                        displayTravelPlaceDetails(it)
                    }
                }
            }

            override fun onFailure(call: Call<TravelPlace>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun displayTravelPlaceDetails(travelPlace: TravelPlace) {
        binding.textCity.text = travelPlace.city
        binding.textDescription.text = travelPlace.description
        binding.textEstimatedCost.text = "Estimated cost: $ ${travelPlace.estimatedCost}"
        binding.textBestSeason.text = "Best season to travel: ${travelPlace.bestSeasonToTravel}"
        Picasso.get().load(travelPlace.imageUrls.firstOrNull()).into(binding.imagePlace)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
