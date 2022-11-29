package uz.gita.my_taxi_jamshid.domain.usecases.impl

import android.location.Address
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import uz.gita.my_taxi_jamshid.domain.repository.Repository
import uz.gita.my_taxi_jamshid.domain.usecases.GetAddressByLocationUseCase
import uz.gita.my_taxi_jamshid.utils.ResultData
import javax.inject.Inject

class GetAddressByLocationUseCaseImpl @Inject constructor(
    private val repository: Repository
) : GetAddressByLocationUseCase {
    override fun getAddressByLocation(latLng: LatLng): Flow<ResultData<Address>> =
        repository.getAddressByLocation(latLng)
}