package com.nodil.diplom.util

import android.location.Location
import android.location.LocationManager

class LocationFilter {
    private val locations = mutableListOf<Location>()

    fun check(location: Location): Boolean {
        locations.add(location)
        if (locations.size <= 14) {
            return true
        }
        val prevLocation = locations.last()
        val meadDistance = meanDistance(14)
        println("Mean $meadDistance")
        if (location.accuracy > 15){
            return false
        }
        if (location.distanceTo(prevLocation) > meadDistance * 5) {
            //return false
        }

        return true
    }

    private fun meanDistance(period: Int): Double {
        val distance = mutableListOf<Float>()

        val last = locations.takeLast(period)
        for (i in 0 until last.size - 1) {
            distance.add(last[i].distanceTo(last[i + 1]))
        }
        return distance.average()
    }
}