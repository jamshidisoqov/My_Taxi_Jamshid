package uz.gita.my_taxi_jamshid.presentation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.directions.route.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uz.gita.my_taxi_jamshid.BuildConfig.MAP_API_KEY
import uz.gita.my_taxi_jamshid.data.models.DestinationData
import uz.gita.my_taxi_jamshid.data.models.RouteData
import uz.gita.my_taxi_jamshid.presentation.screens.details.TripDetailsViewModel
import uz.gita.my_taxi_jamshid.utils.extensions.getMessage
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModelImpl @Inject constructor() : TripDetailsViewModel, ViewModel(),
    RoutingListener {

    override val loadingFlow = MutableSharedFlow<Boolean>()

    override val toastFlow = MutableSharedFlow<String>()

    override val messageFlow = MutableSharedFlow<String>()

    override val errorFlow = MutableSharedFlow<String>()

    override val routesFlow = MutableSharedFlow<RouteData>()

    override fun getRoutesByLocation(destinationData: DestinationData) {
        viewModelScope.launch {
            Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this@TripDetailsViewModelImpl)
                .waypoints(destinationData.start, destinationData.end)
                .key(MAP_API_KEY)
                .build().execute()
        }
    }

    override fun onRoutingFailure(p0: RouteException?) {
        viewModelScope.launch {
            loadingFlow.emit(false)
            errorFlow.emit(p0?.getMessage()!!)
        }
    }

    override fun onRoutingStart() {
        viewModelScope.launch {
            loadingFlow.emit(true)
        }
    }

    override fun onRoutingSuccess(p0: ArrayList<Route>?, p1: Int) {
        viewModelScope.launch {
            loadingFlow.emit(false)
            routesFlow.emit(RouteData(p0, p1))
        }
    }

    override fun onRoutingCancelled() {
        viewModelScope.launch {
            loadingFlow.emit(false)
            messageFlow.emit("Отменено")
        }
    }
}