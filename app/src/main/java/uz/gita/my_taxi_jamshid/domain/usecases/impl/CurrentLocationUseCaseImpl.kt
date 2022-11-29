package uz.gita.my_taxi_jamshid.domain.usecases.impl

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import uz.gita.my_taxi_jamshid.domain.repository.Repository
import uz.gita.my_taxi_jamshid.domain.usecases.CurrentLocationUseCase
import uz.gita.my_taxi_jamshid.utils.ResultData
import javax.inject.Inject

class CurrentLocationUseCaseImpl @Inject constructor(
    private val repository: Repository
): CurrentLocationUseCase {

    override fun getCurrentLocation(): Flow<ResultData<LatLng>> = repository.getCurrentLocation()

}