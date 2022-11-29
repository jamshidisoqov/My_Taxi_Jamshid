package uz.gita.my_taxi_jamshid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.my_taxi_jamshid.domain.usecases.CurrentLocationUseCase
import uz.gita.my_taxi_jamshid.domain.usecases.GetAddressByLocationUseCase
import uz.gita.my_taxi_jamshid.domain.usecases.GetAllTripsUseCase
import uz.gita.my_taxi_jamshid.domain.usecases.impl.CurrentLocationUseCaseImpl
import uz.gita.my_taxi_jamshid.domain.usecases.impl.GetAddressByLocationUseCaseImpl
import uz.gita.my_taxi_jamshid.domain.usecases.impl.GetAllTripsUseCaseImpl

// Created by Jamshid Isoqov an 11/28/2022
@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindCurrentLocation(impl: CurrentLocationUseCaseImpl): CurrentLocationUseCase

    @Binds
    fun bindGetAddressByLocation(impl: GetAddressByLocationUseCaseImpl): GetAddressByLocationUseCase

    @Binds
    fun bindGetAllTrips(impl: GetAllTripsUseCaseImpl): GetAllTripsUseCase

}