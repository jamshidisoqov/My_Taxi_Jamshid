package uz.gita.my_taxi_jamshid.presentation.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import uz.gita.my_taxi_jamshid.directions.MainScreenDirection
import uz.gita.my_taxi_jamshid.domain.usecases.CurrentLocationUseCase
import uz.gita.my_taxi_jamshid.domain.usecases.GetAddressByLocationUseCase
import uz.gita.my_taxi_jamshid.presentation.screens.main.MainViewModel
import uz.gita.my_taxi_jamshid.utils.MAP_DEBOUNCE_TIME_OUT
import uz.gita.my_taxi_jamshid.utils.extensions.getMessage
import javax.inject.Inject

// Created by Jamshid Isoqov on 11/29/2022

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val getAddressByLocation: GetAddressByLocationUseCase,
    private val currentLocationUseCase: CurrentLocationUseCase,
    private val direction: MainScreenDirection
) : MainViewModel, ViewModel() {

    override val loadingFlow = MutableSharedFlow<Boolean>()

    override val toastFlow = MutableSharedFlow<String>()

    override val messageFlow = MutableSharedFlow<String>()

    override val errorFlow = MutableSharedFlow<String>()

    override val addressFlow = MutableSharedFlow<String>()

    override val currentLocationFlow = MutableLiveData(LatLng(41.3341242, 69.2983809))

    @OptIn(FlowPreview::class)
    override fun getAddressByLocation(latLng: LatLng) {
        viewModelScope.launch {
            loadingFlow.emit(true)
            getAddressByLocation.getAddressByLocation(latLng)
                .debounce(MAP_DEBOUNCE_TIME_OUT)
                .collectLatest { result ->
                    result.onSuccess { data ->
                        addressFlow.emit(data.getAddressLine(0))
                    }.onMessage { message ->
                        messageFlow.emit(message)
                    }.onError { error ->
                        errorFlow.emit(error.getMessage())
                    }
                    loadingFlow.emit(false)
                }
        }
    }

    @OptIn(FlowPreview::class)
    override fun requestCurrentLocation() {
        viewModelScope.launch {
            loadingFlow.emit(true)
            currentLocationUseCase.getCurrentLocation()
                .debounce(MAP_DEBOUNCE_TIME_OUT)
                .collectLatest { result ->
                    result.onSuccess { data ->
                        currentLocationFlow.value = data
                        Log.d("TTT", "requestCurrentLocation:Success ")
                    }.onMessage { message ->
                        messageFlow.emit(message)
                        Log.d("TTT", "requestCurrentLocation:${message} ")
                    }.onError { error ->
                        Log.d("TTT", "requestCurrentLocation:${error.message} ")
                        errorFlow.emit(error.getMessage())
                    }
                    loadingFlow.emit(false)
                }
        }
    }

    override fun navigateToTrips() {
        viewModelScope.launch {
            direction.navigateToTrips()
        }
    }
}