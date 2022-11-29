package uz.gita.my_taxi_jamshid.presentation.screens.main

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import uz.gita.my_taxi_jamshid.R
import uz.gita.my_taxi_jamshid.databinding.ScreenMainBinding
import uz.gita.my_taxi_jamshid.presentation.presenter.MainViewModelImpl
import uz.gita.my_taxi_jamshid.utils.EVENT_DEBOUNCE_TIME_OUT
import uz.gita.my_taxi_jamshid.utils.extensions.hideProgress
import uz.gita.my_taxi_jamshid.utils.extensions.showError
import uz.gita.my_taxi_jamshid.utils.extensions.showMessage
import uz.gita.my_taxi_jamshid.utils.extensions.showProgress

// Created by Jamshid Isoqov an 11/29/2022
@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main), GoogleMap.OnMarkerClickListener {

    private val viewBinding: ScreenMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    private lateinit var mGoogleMap: GoogleMap

    private lateinit var centerScreenCoordinate: LatLng

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mapInit()

        viewModel.loadingFlow.onEach {
            if (it) showProgress() else hideProgress()
        }.launchIn(lifecycleScope)

        viewModel.messageFlow.onEach {
            showMessage(it)
        }.launchIn(lifecycleScope)

        viewModel.errorFlow.onEach {
            showError(it)
        }.launchIn(lifecycleScope)

        viewBinding.contentMain.imageMyLocation
            .clicks()
            .debounce(EVENT_DEBOUNCE_TIME_OUT)
            .onEach {
                viewModel.requestCurrentLocation()
            }.launchIn(lifecycleScope)

        viewModel.addressFlow.onEach {
            viewBinding.contentMain.tvAddressName.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.currentLocationFlow.onEach {
            if (this@MainScreen::mGoogleMap.isInitialized) {
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(it, 12f)
                mGoogleMap.animateCamera(cameraUpdate)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewBinding.contentMain.imageMenu.setOnClickListener {
            viewBinding.drawerContainer.openDrawer(GravityCompat.START)
        }

        viewBinding.apply {

            drawerContainer.setRadius(Gravity.START, 35f)
            drawerContainer.setViewScale(Gravity.START, 0.9f)
            drawerContainer.setViewElevation(Gravity.START, 20f)

            navigationView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_trip_history -> {
                        viewModel.navigateToTrips()
                    }
                    R.id.menu_payment_methods -> {

                    }
                    R.id.menu_favorite_addresses -> {

                    }
                }
                true
            }

        }
    }

    override fun onMarkerClick(p0: Marker): Boolean = false

    private fun mapInit() {
        val mapScreen = childFragmentManager.findFragmentById(R.id.container_map) as MapHelper
        mapScreen.getMapAsync(mapScreen)

        mapScreen.onMapReady { googleMap: GoogleMap ->
            mGoogleMap = googleMap
            mGoogleMap.uiSettings.apply {
                isCompassEnabled = false
                isRotateGesturesEnabled = false
                isMyLocationButtonEnabled = true
                isZoomControlsEnabled = true
            }
            mGoogleMap.setOnMarkerClickListener(this)
            mGoogleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            centerScreenCoordinate = mGoogleMap.cameraPosition.target
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(centerScreenCoordinate))
            mGoogleMap.setOnCameraMoveListener {
                centerScreenCoordinate = mGoogleMap.cameraPosition.target
            }
            mGoogleMap.setOnCameraIdleListener {
                viewModel.getAddressByLocation(
                    LatLng(
                        centerScreenCoordinate.latitude,
                        centerScreenCoordinate.longitude
                    )
                )
            }
        }
    }

}