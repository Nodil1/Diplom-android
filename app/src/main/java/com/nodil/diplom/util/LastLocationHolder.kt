package com.nodil.diplom.util

import android.location.Location

object LastLocationHolder {
    private var lastLocation: Location? = null

    private val listeners = mutableListOf<(Location)->Unit>()
    fun addListener(listener: (Location)->Unit) {
        listeners.add(listener)
    }
    fun getLocation(): Location? = lastLocation
    fun setLocation(location: Location) {
        lastLocation = location
        listeners.onEach {
            it(location)
        }
    }
}