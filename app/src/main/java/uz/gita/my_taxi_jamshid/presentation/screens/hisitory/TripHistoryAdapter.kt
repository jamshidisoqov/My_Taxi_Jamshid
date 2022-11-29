package uz.gita.my_taxi_jamshid.presentation.screens.hisitory

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import uz.gita.my_taxi_jamshid.R
import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.data.models.TripDate
import uz.gita.my_taxi_jamshid.data.models.TripWithDate
import uz.gita.my_taxi_jamshid.databinding.ListItemTripsBinding
import uz.gita.my_taxi_jamshid.databinding.ListItemTripsHeaderBinding
import uz.gita.my_taxi_jamshid.utils.extensions.inflate

// Created by Jamshid Isoqov on 11/29/2022

private val tripItemCallback = object : DiffUtil.ItemCallback<TripWithDate>() {
    override fun areItemsTheSame(oldItem: TripWithDate, newItem: TripWithDate): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TripWithDate, newItem: TripWithDate): Boolean {
        return if (oldItem is TripData && newItem is TripData) {
            oldItem.id == newItem.id &&
                    oldItem.tripDate == newItem.tripDate &&
                    oldItem.tripTime == newItem.tripTime &&
                    oldItem.carNumber == newItem.carNumber &&
                    oldItem.tripPrice == newItem.tripPrice
        } else if (oldItem is TripDate && newItem is TripDate) {
            oldItem.tripDate == newItem.tripDate
        } else false
    }

}

open class TripHistoryAdapter :
    ListAdapter<TripWithDate, TripHistoryAdapter.BaseViewHolder>(tripItemCallback) {

    private var itemClickListener: ((TripData) -> Unit)? = null

    fun setItemClickListener(block: (TripData) -> Unit) {
        itemClickListener = block
    }

    abstract inner class BaseViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val data = getItem(absoluteAdapterPosition)
                if (data is TripData) {
                    itemClickListener?.invoke(data)
                }
            }
        }

        abstract fun <T> onBind(data: T)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == 1) {
            ViewHolderTrips(ListItemTripsBinding.bind(parent.inflate(R.layout.list_item_trips))).TripChild()
        } else {
            return ViewHolderTripDate(ListItemTripsHeaderBinding.bind(parent.inflate(R.layout.list_item_trips_header))).TripChildDate()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.onBind(getItem(position))

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TripData -> 1
            is TripDate -> 2
        }
    }
}