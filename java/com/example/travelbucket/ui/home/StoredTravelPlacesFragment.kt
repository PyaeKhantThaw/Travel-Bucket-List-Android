package com.example.travelbucket.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelbucket.data.TravelPlaceDatabase
import com.example.travelbucket.data.TravelPlaceRepository
import com.example.travelbucket.databinding.FragmentStoredTravelPlacesBinding



class StoredTravelPlacesFragment : Fragment() {

    private var _binding: FragmentStoredTravelPlacesBinding? = null
    private val binding get() = _binding!!
    private lateinit var travelPlaceViewModel: TravelPlaceViewModel
    private lateinit var adapter: StoredTravelPlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoredTravelPlacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        adapter = StoredTravelPlaceAdapter { travelPlace ->
            // Handle delete action
            travelPlaceViewModel.delete(travelPlace)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Initialize ViewModel and observe data
        val repository = TravelPlaceRepository(TravelPlaceDatabase.getDatabase(requireContext()).travelPlaceDao())
        val factory = TravelPlaceViewModelFactory(repository)
        travelPlaceViewModel = ViewModelProvider(this, factory).get(TravelPlaceViewModel::class.java)

        travelPlaceViewModel.allTravelPlaces.observe(viewLifecycleOwner) { travelPlaces ->
            adapter.submitList(travelPlaces)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



