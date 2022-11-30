package uz.gita.my_taxi_jamshid.directions.impl

import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.directions.TripsScreenDirection
import uz.gita.my_taxi_jamshid.navigation.Navigator
import uz.gita.my_taxi_jamshid.presentation.screens.hisitory.TripHistoryScreenDirections
import javax.inject.Inject

class TripsScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : TripsScreenDirection {
    override suspend fun navigateToTripsDetails(tripData: TripData) {
        navigator.navigateTo(
            TripHistoryScreenDirections.actionTripHistoryScreen2ToTripDetailScreen(
                tripData
            )
        )
    }
}