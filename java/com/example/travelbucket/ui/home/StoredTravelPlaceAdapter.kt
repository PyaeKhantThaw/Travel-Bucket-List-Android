package com.example.travelbucket.ui.home

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbucket.R
import com.example.travelbucket.data.TravelPlace
import com.example.travelbucket.databinding.StoredTravelPlaceBinding
import com.squareup.picasso.Picasso
import java.io.File

class StoredTravelPlaceAdapter(
    private val onDeleteClick: (TravelPlace) -> Unit
) : ListAdapter<TravelPlace, StoredTravelPlaceAdapter.StoredTravelPlaceViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoredTravelPlaceViewHolder {
        val binding = StoredTravelPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoredTravelPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoredTravelPlaceViewHolder, position: Int) {
        val travelPlace = getItem(position)
        holder.bind(travelPlace)

        // Handle delete button click
        holder.binding.imageView2.setOnClickListener {
            onDeleteClick(travelPlace)
        }
    }

    class StoredTravelPlaceViewHolder(val binding: StoredTravelPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(travelPlace: TravelPlace) {
            binding.textCity.text = travelPlace.city
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TravelPlace>() {
        override fun areItemsTheSame(oldItem: TravelPlace, newItem: TravelPlace): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: TravelPlace, newItem: TravelPlace): Boolean {
            return oldItem == newItem
        }
    }
}
