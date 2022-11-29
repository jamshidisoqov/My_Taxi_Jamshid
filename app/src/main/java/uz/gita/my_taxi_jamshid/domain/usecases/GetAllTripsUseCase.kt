package uz.gita.my_taxi_jamshid.domain.usecases

import uz.gita.my_taxi_jamshid.data.models.TripWithDate

// Created by Jamshid Isoqov on 11/29/2022
interface GetAllTripsUseCase {

    fun getAllTrips(): List<TripWithDate>

}