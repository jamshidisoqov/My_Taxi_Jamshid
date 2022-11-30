package uz.gita.my_taxi_jamshid.presentation.screens.hisitory

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import uz.gita.my_taxi_jamshid.R
import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.data.models.TripDate
import uz.gita.my_taxi_jamshid.data.models.TripWithDate
import uz.gita.my_taxi_jamshid.data.models.enums.CarType
import uz.gita.my_taxi_jamshid.databinding.ListItemTripsBinding
import uz.gita.my_taxi_jamshid.databinding.ListItemTripsHeaderBinding
import uz.gita.my_taxi_jamshid.utils.extensions.getFinanceType
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

class TripHistoryAdapter :
    ListAdapter<TripWithDate, BaseViewHolder>(tripItemCallback) {

    private var itemClickListener: ((TripData) -> Unit)? = null

    fun setItemClickListener(block: (TripData) -> Unit) {
        itemClickListener = block
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == 1) {
            ViewHolderTrips(ListItemTripsBinding.bind(parent.inflate(R.layout.list_item_trips)))
        } else {
            return ViewHolderTripDate(ListItemTripsHeaderBinding.bind(parent.inflate(R.layout.list_item_trips_header)))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(getItem(position)) {
            itemClickListener?.invoke(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TripData -> 1
            is TripDate -> 2
        }
    }
}


abstract class BaseViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun <T> onBind(data: T, block: (TripData) -> Unit)
}

class ViewHolderTrips(private val binding: ListItemTripsBinding) : BaseViewHolder(binding) {

    override fun <T> onBind(data: T, block: (TripData) -> Unit) {
        val trip = data as TripData
        binding.apply {
            tripCardView.setOnClickListener {
                block.invoke(trip)
            }
            tvFirstAddress.text = trip.destinationData.fromWhere
            tvSecondAddress.text = trip.destinationData.toWhere
            tvTime.text = trip.tripTime
            tvMoney.text = trip.tripPrice.getFinanceType()
            imageCarType.setImageResource(
                when (trip.carType) {
                    CarType.YELLOW -> R.drawable.yellow_car
                    CarType.WHITE -> R.drawable.white_car
                    CarType.BLACK -> R.drawable.black_car
                }
            )
        }
    }

}

class ViewHolderTripDate(private val binding: ListItemTripsHeaderBinding) :
    BaseViewHolder(binding) {

    override fun <T> onBind(data: T, block: (TripData) -> Unit) {
        val date = data as TripDate
        binding.apply {
            tvDate.text = date.tripDate
        }
    }
}