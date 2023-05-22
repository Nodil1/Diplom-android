package com.nodil.diplom.util

import android.location.Location

class KalmanFilter {

    private var x: Double = 0.0 // координата широты
    private var y: Double = 0.0 // координата долготы
    private var Px: Double = 1.0 // ковариационная матрица для x
    private var Py: Double = 1.0 // ковариационная матрица для y
    private val Q: Double = 0.000001 // процессный шум
    private val R: Double = 0.01 // измерительный шум

    fun filter(location: Location): Location {
        val latitude = location.latitude
        val longitude = location.longitude

        // Применяем фильтр Калмана к координате широты
        val Kx = Px / (Px + R)
        x += Kx * (latitude - x)
        Px = (1 - Kx) * Px + Q

        // Применяем фильтр Калмана к координате долготы
        val Ky = Py / (Py + R)
        y += Ky * (longitude - y)
        Py = (1 - Ky) * Py + Q

        // Создаем новый объект Location с отфильтрованными координатами
        val filteredLocation = Location(location)
        filteredLocation.latitude = y
        filteredLocation.longitude = x

        return filteredLocation
    }
}