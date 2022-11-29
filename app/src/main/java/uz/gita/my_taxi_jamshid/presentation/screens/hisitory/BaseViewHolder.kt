package uz.gita.my_taxi_jamshid.presentation.screens.hisitory

import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.data.models.TripDate
import uz.gita.my_taxi_jamshid.databinding.ListItemTripsBinding
import uz.gita.my_taxi_jamshid.databinding.ListItemTripsHeaderBinding
import uz.gita.my_taxi_jamshid.utils.extensions.getFinanceType

// Created by Jamshid Isoqov on 11/30/2022


class ViewHolderTrips(private val binding: ListItemTripsBinding) : TripHistoryAdapter() {
    inner class TripChild:TripHistoryAdapter.BaseViewHolder(binding){
        override fun <T> onBind(data: T) {
            val trip = data as TripData
            binding.apply {
                tvFirstAddress.text = trip.destinationData.fromWhere
                tvSecondAddress.text = trip.destinationData.toWhere
                tvTime.text = trip.tripTime
                tvMoney.text = trip.tripPrice.getFinanceType()
            }
        }

    }
}
class ViewHolderTripDate(private val binding: ListItemTripsHeaderBinding) :
    TripHistoryAdapter() {

    inner class TripChildDate:TripHistoryAdapter.BaseViewHolder(binding){
        override fun <T> onBind(data: T) {
            val date = data as TripDate
            binding.apply {
                tvDate.text = date.tripDate
            }
        }
    }
}