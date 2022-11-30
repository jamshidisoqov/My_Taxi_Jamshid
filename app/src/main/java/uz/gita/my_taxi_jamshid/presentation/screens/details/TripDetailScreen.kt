package uz.gita.my_taxi_jamshid.presentation.screens.details

import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.my_taxi_jamshid.R
import uz.gita.my_taxi_jamshid.data.models.DestinationData
import uz.gita.my_taxi_jamshid.databinding.ScreenTripDetailsBinding
import uz.gita.my_taxi_jamshid.presentation.presenter.TripDetailsViewModelImpl
import uz.gita.my_taxi_jamshid.presentation.screens.main.MapHelper
import uz.gita.my_taxi_jamshid.utils.extensions.*

// Created by Jamshid Isoqov an 11/29/2022
@AndroidEntryPoint
class TripDetailScreen : Fragment(R.layout.screen_trip_details) {

    private lateinit var googleMap: GoogleMap
    private var line: Polyline? = null
    private var locationManager: LocationManager? = null

    private val viewBinding: ScreenTripDetailsBinding by viewBinding()

    private val viewModel: TripDetailsViewModel by viewModels<TripDetailsViewModelImpl>()

    private val args: TripDetailScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val destination = args.trip.destinationData

        mapInit(destination)

        viewModel.loadingFlow.onEach {
            if (it) showProgress() else hideProgress()
        }.launchIn(lifecycleScope)

        viewModel.messageFlow.onEach {
            showMessage(it)
        }.launchIn(lifecycleScope)

        viewModel.errorFlow.onEach {
            showError(it)
        }.launchIn(lifecycleScope)

        viewModel.routesFlow.onEach {
            val listPoints: List<LatLng> = it.routeList?.get(0)!!.points
            val options = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
            val iterator = listPoints.iterator()
            while (iterator.hasNext()) {
                val data = iterator.next()
                options.add(data)
            }
            line?.remove()
            line = googleMap.addPolyline(options)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it.routeList!![0].latLgnBounds.center))
            val builder = LatLngBounds.Builder()
            builder.include(destination.start)
            builder.include(destination.end)
            val bounds = builder.build()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 60))

        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.getRoutesByLocation(args.trip.destinationData)

    }

    override fun onPause() {
        locationManager = null
        super.onPause()
    }

    private fun mapInit(destination: DestinationData) {
        val mapScreen =
            childFragmentManager.findFragmentById(R.id.trip_detail_map_container) as MapHelper
        mapScreen.getMapAsync(mapScreen)

        val start = destination.start
        val end = destination.end

        mapScreen.onMapReady {
            googleMap = it
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            googleMap.uiSettings.apply {
                isCompassEnabled = false
            }
            googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions().position(start)
                    .title(args.trip.destinationData.fromWhere)
                    .icon(bitmapFromVector(R.drawable.ic_target_red))
            )
            googleMap.addMarker(
                MarkerOptions().position(end)
                    .title(args.trip.destinationData.toWhere)
                    .icon(bitmapFromVector(R.drawable.ic_target_blue))
            )
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 16F))
        }
    }
}
