package uz.gita.my_taxi_jamshid.domain.usecases

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import uz.gita.my_taxi_jamshid.utils.ResultData

// Created by Jamshid Isoqov on 11/29/2022
interface CurrentLocationUseCase {

    fun getCurrentLocation():Flow<ResultData<LatLng>>

}