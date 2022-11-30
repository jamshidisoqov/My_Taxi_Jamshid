package uz.gita.my_taxi_jamshid.domain.usecases.impl

import timber.log.Timber
import uz.gita.my_taxi_jamshid.data.models.TripDate
import uz.gita.my_taxi_jamshid.data.models.TripWithDate
import uz.gita.my_taxi_jamshid.domain.repository.Repository
import uz.gita.my_taxi_jamshid.domain.usecases.GetAllTripsUseCase
import javax.inject.Inject

class GetAllTripsUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetAllTripsUseCase {
    override fun getAllTrips(): List<TripWithDate> {

        val tripMap = repository.getAllTrips().groupBy {
            it.tripDate
        }
        Timber.tag("TTT").d("getAllTrips: $tripMap")
        val trips = ArrayList<TripWithDate>()
        tripMap.keys.forEach {
            trips.add(TripDate(it))
            trips.addAll(tripMap[it]!!)
        }
        return trips

    }
}