package uz.gita.my_taxi_jamshid.presentation.screens.main

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

// Created by Jamshid Isoqov an 11/28/2022
class MapHelper: SupportMapFragment(), OnMapReadyCallback {

    private var map: (googleMap: GoogleMap) -> Unit = {}

    fun onMapReady(map: (googleMap: GoogleMap) -> Unit) {
        this.map = map
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map.invoke(googleMap)
    }
}