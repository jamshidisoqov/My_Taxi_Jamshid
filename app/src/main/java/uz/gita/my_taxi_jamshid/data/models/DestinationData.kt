package uz.gita.my_taxi_jamshid.data.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class DestinationData(
    val fromWhere: String,
    val toWhere: String,
    val start: LatLng,
    val end: LatLng
): Parcelable