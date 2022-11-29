package uz.gita.my_taxi_jamshid.presentation.screens.hisitory

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.data.models.TripWithDate

// Created by Jamshid Isoqov an 11/29/2022
interface TripHistoryViewModel {

    val loadingFlow: SharedFlow<Boolean>

    val toastFlow: SharedFlow<String>

    val messageFlow: SharedFlow<String>

    val errorFlow: SharedFlow<String>

    val tripHistoryFlow: StateFlow<List<TripWithDate>>

    fun navigateToTripDetails(tripData: TripData)

    fun getAllTrips()

}