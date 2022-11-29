package uz.gita.my_taxi_jamshid.domain.usecases

import android.location.Address
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import uz.gita.my_taxi_jamshid.utils.ResultData

// Created by Jamshid Isoqov on 11/29/2022
interface GetAddressByLocationUseCase {

    fun getAddressByLocation(latLng: LatLng): Flow<ResultData<Address>>

}