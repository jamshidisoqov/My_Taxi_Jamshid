package uz.gita.my_taxi_jamshid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.my_taxi_jamshid.directions.MainScreenDirection
import uz.gita.my_taxi_jamshid.directions.TripsScreenDirection
import uz.gita.my_taxi_jamshid.directions.impl.MainScreenDirectionImpl
import uz.gita.my_taxi_jamshid.directions.impl.TripsScreenDirectionImpl

// Created by Jamshid Isoqov an 11/28/2022
@Module
@InstallIn(ViewModelComponent::class)
interface DirectionsModule {

    @Binds
    fun bindMainScreenDirections(impl: MainScreenDirectionImpl): MainScreenDirection

    @Binds
    fun bindTripsScreenDirections(impl: TripsScreenDirectionImpl): TripsScreenDirection

}