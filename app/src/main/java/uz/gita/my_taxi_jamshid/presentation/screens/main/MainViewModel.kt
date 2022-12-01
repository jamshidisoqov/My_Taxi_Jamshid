package uz.gita.my_taxi_jamshid.presentation.screens.main

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

// Created by Jamshid Isoqov an 11/29/2022
interface MainViewModel {

    val loadingFlow: SharedFlow<Boolean>

    val toastFlow: SharedFlow<String>

    val messageFlow: SharedFlow<String>

    val errorFlow: SharedFlow<String>

    val addressFlow: SharedFlow<String>

    val currentLocationFlow: LiveData<LatLng>

    fun getAddressByLocation(latLng: LatLng)

    fun requestCurrentLocation()

    fun navigateToTrips()
}