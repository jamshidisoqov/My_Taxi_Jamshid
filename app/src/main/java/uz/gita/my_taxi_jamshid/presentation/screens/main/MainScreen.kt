package uz.gita.my_taxi_jamshid.presentation.screens.main

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.ldralighieri.corbind.view.clicks
import uz.gita.my_taxi_jamshid.BuildConfig
import uz.gita.my_taxi_jamshid.R
import uz.gita.my_taxi_jamshid.databinding.ScreenMainBinding
import uz.gita.my_taxi_jamshid.presentation.presenter.MainViewModelImpl
import uz.gita.my_taxi_jamshid.utils.ACCESS_FINE_LOCATION
import uz.gita.my_taxi_jamshid.utils.EVENT_DEBOUNCE_TIME_OUT
import uz.gita.my_taxi_jamshid.utils.extensions.*


// Created by Jamshid Isoqov an 11/29/2022
@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main), GoogleMap.OnMarkerClickListener {

    private val viewBinding: ScreenMainBinding by viewBinding()

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    private lateinit var mGoogleMap: GoogleMap

    private lateinit var centerScreenCoordinate: LatLng

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                permissionApprovedSnackBar()
                viewModel.requestCurrentLocation()
            }
            else
                permissionDeniedSnackBar()
        }

    private var job: Job? = null

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mapInit()

        viewModel.loadingFlow.onEach {
            viewBinding.apply {
                if (it) {
                    contentMain.progressSpinKit.visible()
                    contentMain.imageMyLocation.inVisible()
                } else {
                    contentMain.progressSpinKit.inVisible()
                    contentMain.imageMyLocation.visible()
                }
            }
        }.launchIn(lifecycleScope)

        viewModel.messageFlow.onEach {
            showMessage(it)
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

        viewModel.currentLocationFlow.observe(viewLifecycleOwner) {
            Log.d("TTT", "onViewCreated: $it")
            if (this@MainScreen::mGoogleMap.isInitialized) {
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(it, 16f)
                mGoogleMap.animateCamera(cameraUpdate)
            } else {
                centerScreenCoordinate = it
            }
        }

        viewBinding.contentMain.imageMenu.setOnClickListener {
            viewBinding.drawerContainer.openDrawer(GravityCompat.START)
        }

        viewBinding.contentMain.imageMyLocation
            .clicks()
            .debounce(100L)
            .onEach {
                if (isLocationEnabled()) {
                    if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Log.d("TTT", "onViewCreated:Current")
                        viewModel.requestCurrentLocation()
                    } else {
                      locationRequest()
                    }
                } else {
                    showMessage(getString(R.string.location_or_network_disable))
                }
            }.launchIn(lifecycleScope)
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
                drawerContainer.closeDrawer(GravityCompat.START)
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
                job?.cancel()
                job = viewLifecycleOwner.lifecycleScope.launch {
                    delay(1000L)
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

    private fun locationRequest() {
        if (hasPermission(ACCESS_FINE_LOCATION)) viewModel.requestCurrentLocation()
        else requestPermission.launch(ACCESS_FINE_LOCATION)
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle(getString(R.string.enable_location))
            .setMessage(getString(R.string.enable_location_message))
            .setPositiveButton(getString(R.string.location_settings)) { _, _ ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        dialog.show()
    }

    private fun permissionApprovedSnackBar() {
        Snackbar.make(
            viewBinding.root, R.string.permission_approved_explanation,
            BaseTransientBottomBar.LENGTH_LONG
        ).show()
    }

    private fun permissionDeniedSnackBar() {
        Snackbar.make(
            viewBinding.root,
            R.string.fine_permission_denied_explanation,
            BaseTransientBottomBar.LENGTH_LONG
        )
            .setAction(R.string.settings) { launchSettings() }
            .setActionTextColor(Color.WHITE)
            .show()
    }

    private fun launchSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
            "package",
            BuildConfig.APPLICATION_ID, null
        )
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}