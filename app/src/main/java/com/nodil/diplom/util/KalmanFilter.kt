package com.nodil.diplom.util

import android.location.Location

class KalmanFilter() {

    private var currentState: State? = null

    fun getLocation(): Location {
        return currentState!!.location
    }

    fun update(location: Location) {
        if (currentState == null) {
            currentState = State(location, 1.0, 1.0)
            return
        }

        val kalmanGain = currentState!!.variance / (currentState!!.variance + 10.0)
        val newLocation = Location("KalmanFilter")
        newLocation.accuracy = location.accuracy
        newLocation.latitude =
            (location.latitude * kalmanGain) + (currentState!!.location.latitude * (1 - kalmanGain))
        newLocation.longitude =
            (location.longitude * kalmanGain) + (currentState!!.location.longitude * (1 - kalmanGain))


        val newVariance = (1 - kalmanGain) * currentState!!.variance
        currentState = State(newLocation, newVariance, kalmanGain)
    }

    data class State(val location: Location, val variance: Double, val kalmanGain: Double)
}