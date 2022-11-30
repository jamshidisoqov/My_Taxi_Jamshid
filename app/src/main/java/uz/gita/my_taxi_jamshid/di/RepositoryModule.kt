package uz.gita.my_taxi_jamshid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.my_taxi_jamshid.domain.repository.Repository
import uz.gita.my_taxi_jamshid.domain.repository.impl.RepositoryImpl
import javax.inject.Singleton

// Created by Jamshid Isoqov an 11/28/2022
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindRepository(impl: RepositoryImpl): Repository

}