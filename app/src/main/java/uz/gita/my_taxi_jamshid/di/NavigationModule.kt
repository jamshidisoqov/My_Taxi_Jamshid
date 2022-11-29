package uz.gita.my_taxi_jamshid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.my_taxi_jamshid.navigation.NavigationDispatcher
import uz.gita.my_taxi_jamshid.navigation.NavigationHandler
import uz.gita.my_taxi_jamshid.navigation.Navigator
import javax.inject.Singleton

// Created by Jamshid Isoqov an 11/28/2022

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun navigator(dispatcher: NavigationDispatcher): Navigator

    @[Binds Singleton]
    fun navigatorHandler(dispatcher: NavigationDispatcher): NavigationHandler

}