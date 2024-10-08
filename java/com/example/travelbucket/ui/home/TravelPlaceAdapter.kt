
package com.example.travelbucket.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.travelbucket.data.TravelPlace
import com.example.travelbucket.databinding.ItemTravelPlaceBinding
import com.squareup.picasso.Picasso


class TravelPlaceAdapter(
    private val onItemClick: (TravelPlace) -> Unit
) : ListAdapter<TravelPlace, TravelPlaceAdapter.TravelPlaceViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelPlaceViewHolder {
        val binding = ItemTravelPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TravelPlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TravelPlaceViewHolder, position: Int) {
        val travelPlace = getItem(position)
        holder.bind(travelPlace)

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClick(travelPlace)
        }
    }

    class TravelPlaceViewHolder(private val binding: ItemTravelPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(travelPlace: TravelPlace) {
            binding.textCity.text = travelPlace.city
            Picasso.get().load(travelPlace.imageUrls.firstOrNull()).into(binding.imagePlace)
        }
    }

    class DiffCallback : androidx.recyclerview.widget.DiffUtil.ItemCallback<TravelPlace>() {
        override fun areItemsTheSame(oldItem: TravelPlace, newItem: TravelPlace): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: TravelPlace, newItem: TravelPlace): Boolean {
            return oldItem == newItem
        }
    }
}
