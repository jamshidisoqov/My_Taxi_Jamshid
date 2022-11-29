package uz.gita.my_taxi_jamshid.directions.impl

import uz.gita.my_taxi_jamshid.directions.MainScreenDirection
import uz.gita.my_taxi_jamshid.navigation.Navigator
import uz.gita.my_taxi_jamshid.presentation.screens.main.MainScreenDirections
import javax.inject.Inject

class MainScreenDirectionImpl @Inject constructor(
    private val navigator: Navigator
) : MainScreenDirection {
    override suspend fun navigateToTrips() {
        navigator.navigateTo(MainScreenDirections.actionMainScreenToTripHistoryScreen2())
    }
}