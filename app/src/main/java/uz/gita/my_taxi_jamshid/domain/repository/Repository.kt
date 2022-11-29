package uz.gita.my_taxi_jamshid.domain.repository

import android.location.Address
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.utils.ResultData

// Created by Jamshid Isoqov an 11/28/2022
interface Repository {

    fun getCurrentLocation(): Flow<ResultData<LatLng>>

    fun getAddressByLocation(latLng: LatLng): Flow<ResultData<Address>>

    fun getAllTrips(): List<TripData>

}