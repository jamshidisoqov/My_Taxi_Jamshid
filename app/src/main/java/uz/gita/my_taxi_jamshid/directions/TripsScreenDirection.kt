package uz.gita.my_taxi_jamshid.directions

import uz.gita.my_taxi_jamshid.data.models.TripData

// Created by Jamshid Isoqov on 11/29/2022
interface TripsScreenDirection {

    suspend fun navigateToTripsDetails(tripData: TripData)

}