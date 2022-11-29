package uz.gita.my_taxi_jamshid.data.source

import com.google.android.gms.maps.model.LatLng
import uz.gita.my_taxi_jamshid.data.models.DestinationData
import uz.gita.my_taxi_jamshid.data.models.TripData
import uz.gita.my_taxi_jamshid.data.models.enums.CarType

// Created by Jamshid Isoqov an 11/28/2022
object LocalSource {

    val tripsList = listOf(
        TripData(
            id = 1,
            tripDate = "6 Сентябрь, Вторник",
            destinationData = DestinationData(
                fromWhere = "ICE CITY",
                toWhere = "Seoul National Park in Tashkent",
                start = LatLng(41.2904584, 69.2493869),
                end = LatLng(41.2934779, 69.2502242),
            ),
            tripTime = "18:47",
            tripPrice = 17_300.0,
            carType = CarType.BLACK,
            carNumber = "95 A 484 CA"
        ),
        TripData(
            id = 2,
            tripDate = "6 Сентябрь, Вторник",
            destinationData = DestinationData(
                fromWhere = "Ашхабад Парк",
                toWhere = "Al Kamil O`quv markazi",
                start = LatLng(41.2994952, 69.2836971),
                end = LatLng(41.3396279, 69.2857481),
            ),
            tripTime = "21:12",
            tripPrice = 10_000.0,
            carType = CarType.YELLOW,
            carNumber = "01 A 078 CA"
        ),
        TripData(
            id = 3,
            tripDate = "7 Сентябрь, Среда",
            destinationData = DestinationData(
                fromWhere = "Оққўрғон (маҳаллий) жомеъ масжиди",
                toWhere = "NevroServis Medical Clinic",
                start = LatLng(41.3341242, 69.2983809),
                end = LatLng(41.3122451, 69.1784813),
            ),
            tripTime = "22:12",
            tripPrice = 12_500.0,
            carType = CarType.WHITE,
            carNumber = "01 A 081 KA"
        ),
        TripData(
            id = 4,
            tripDate = "7 Сентябрь, Среда",
            destinationData = DestinationData(
                fromWhere = "Яшнабадский район, улица  Ташкент",
                toWhere = "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
                start = LatLng(41.3125405, 69.2212762),
                end = LatLng(41.3539966, 69.2159131),
            ),
            tripTime = "19:24",
            tripPrice = 14_800.0,
            carType = CarType.YELLOW,
            carNumber = "01 A 078 CA"
        ),
        TripData(
            id = 5,
            tripDate = "6 Сентябрь, Вторник",
            destinationData = DestinationData(
                fromWhere = "ICE CITY",
                toWhere = "Seoul National Park in Tashkent",
                start = LatLng(41.2904584, 69.2493869),
                end = LatLng(41.2934779, 69.2502242),
            ),
            tripTime = "18:47",
            tripPrice = 17_300.0,
            carType = CarType.BLACK,
            carNumber = "95 A 484 CA"
        ),
        TripData(
            id = 6,
            tripDate = "6 Сентябрь, Вторник",
            destinationData = DestinationData(
                fromWhere = "Ашхабад Парк",
                toWhere = "Al Kamil O`quv markazi",
                start = LatLng(41.2994952, 69.2836971),
                end = LatLng(41.3396279, 69.2857481),
            ),
            tripTime = "21:12",
            tripPrice = 10_000.0,
            carType = CarType.YELLOW,
            carNumber = "01 A 078 CA"
        ),
        TripData(
            id = 7,
            tripDate = "7 Сентябрь, Среда",
            destinationData = DestinationData(
                fromWhere = "Оққўрғон (маҳаллий) жомеъ масжиди",
                toWhere = "NevroServis Medical Clinic",
                start = LatLng(41.3341242, 69.2983809),
                end = LatLng(41.3122451, 69.1784813),
            ),
            tripTime = "22:12",
            tripPrice = 12_500.0,
            carType = CarType.WHITE,
            carNumber = "01 A 081 KA"
        ),
        TripData(
            id = 8,
            tripDate = "7 Сентябрь, Среда",
            destinationData = DestinationData(
                fromWhere = "Яшнабадский район, улица  Ташкент",
                toWhere = "Юнусабадский район, м-в юнусабад-19, ул. дехканабад, 17",
                start = LatLng(41.3125405, 69.2212762),
                end = LatLng(41.3539966, 69.2159131),
            ),
            tripTime = "19:24",
            tripPrice = 14_800.0,
            carType = CarType.YELLOW,
            carNumber = "01 A 078 CA"
        )

    )
}