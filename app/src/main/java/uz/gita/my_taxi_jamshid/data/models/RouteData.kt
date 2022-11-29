package uz.gita.my_taxi_jamshid.data.models

import com.directions.route.Route
import java.util.ArrayList

data class RouteData(
    var routeList: ArrayList<Route>?,
    var shortestRouteIndex: Int
)