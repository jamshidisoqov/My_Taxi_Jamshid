package uz.gita.my_taxi_jamshid.data.models

import uz.gita.my_taxi_jamshid.data.models.enums.CarType

data class TripData(
    val id:Long,
    var tripDate: String,
    val destinationData: DestinationData,
    val tripTime: String,
    val tripPrice: Double,
    val carType: CarType,
    val carNumber:String
)