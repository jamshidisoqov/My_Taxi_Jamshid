package uz.gita.my_taxi_jamshid.presentation.screens.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import uz.gita.my_taxi_jamshid.data.models.DestinationData
import uz.gita.my_taxi_jamshid.data.models.RouteData

// Created by Jamshid Isoqov on 11/29/2022
interface TripDetailsViewModel {

    val loadingFlow: SharedFlow<Boolean>

    val toastFlow: SharedFlow<String>

    val messageFlow: SharedFlow<String>

    val errorFlow: SharedFlow<String>

    val routesFlow:SharedFlow<RouteData>

    fun getRoutesByLocation(destinationData: DestinationData)


}