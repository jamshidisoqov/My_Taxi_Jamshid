package uz.gita.my_taxi_jamshid.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import uz.gita.my_taxi_jamshid.data.models.enums.CarType

@Parcelize
data class TripData(
    val id: Long,
    var tripDate: String,
    val destinationData: DestinationData,
    val tripTime: String,
    val tripPrice: Double,
    val carType: CarType,
    val carNumber: String
) : Parcelable, TripWithDate