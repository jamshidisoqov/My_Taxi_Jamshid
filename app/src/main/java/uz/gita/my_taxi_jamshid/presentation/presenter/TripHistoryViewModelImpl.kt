package uz.gita.my_taxi_jamshid.presentation.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.data.models.TripWithDate
import uz.gita.my_taxi_jamshid.directions.TripsScreenDirection
import uz.gita.my_taxi_jamshid.domain.usecases.GetAllTripsUseCase
import uz.gita.my_taxi_jamshid.presentation.screens.hisitory.TripHistoryViewModel
import uz.gita.my_taxi_jamshid.utils.hasConnection
import javax.inject.Inject

@HiltViewModel
class TripHistoryViewModelImpl @Inject constructor(
    private val tripsUseCase: GetAllTripsUseCase,
    private val direction: TripsScreenDirection
) : TripHistoryViewModel, ViewModel() {

    override val loadingFlow = MutableSharedFlow<Boolean>()

    override val toastFlow = MutableSharedFlow<String>()

    override val messageFlow = MutableSharedFlow<String>()

    override val errorFlow = MutableSharedFlow<String>()


    override val tripHistoryFlow = MutableStateFlow<List<TripWithDate>>(emptyList())


    override fun navigateToTripDetails(tripData: TripData) {
        viewModelScope.launch {
            direction.navigateToTripsDetails(tripData)
        }
    }

    override fun getAllTrips() {
        viewModelScope.launch {
            loadingFlow.emit(true)
            delay(2000L)
            if (hasConnection()) {
                tripHistoryFlow.emit(tripsUseCase.getAllTrips())
            }else{
                messageFlow.emit("Без интернета!")
            }
            loadingFlow.emit(false)
        }
    }
}