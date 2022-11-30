package uz.gita.my_taxi_jamshid.utils.extensions

// Created by Jamshid Isoqov an 10/12/2022
fun Throwable.getMessage() = this.message ?: "Неизвестная ошибка"
