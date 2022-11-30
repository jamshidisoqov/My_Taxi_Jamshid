package uz.gita.my_taxi_jamshid.domain.repository.impl

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.my_taxi_jamshid.data.source.LocalSource
import uz.gita.my_taxi_jamshid.domain.repository.Repository
import uz.gita.my_taxi_jamshid.utils.ResultData
import uz.gita.my_taxi_jamshid.utils.hasConnection
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val geocoder: Geocoder,
    private val fusedLocationClient: FusedLocationProviderClient
) : Repository {

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Flow<ResultData<LatLng>> =
        callbackFlow<ResultData<LatLng>> {
            if (hasConnection()) {
                fusedLocationClient.lastLocation.addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        trySend(
                            ResultData.Success(
                                LatLng(
                                    task.result.latitude,
                                    task.result.longitude
                                )
                            )
                        )
                    } else {
                        trySend(ResultData.Error(task.exception!!))
                    }
                }.addOnFailureListener { error ->
                    trySend(ResultData.Error(error))
                }
            } else {
                trySend(ResultData.Message("Без интернета!"))
            }
            awaitClose { }
        }.flowOn(Dispatchers.IO)

    override fun getAddressByLocation(latLng: LatLng): Flow<ResultData<Address>> =
        flow<ResultData<Address>> {
            if (hasConnection()) {
                val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (address.isNotEmpty())
                    emit(ResultData.Success(address[0]))
                else{
                    emit(ResultData.Error(Throwable("Данные недоступны")))
                }
            } else {
                emit(ResultData.Message("Без интернета!"))
            }

        }.flowOn(Dispatchers.IO)

    override fun getAllTrips() = LocalSource.tripsList
}