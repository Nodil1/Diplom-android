package com.nodil.diplom.domain.models

data class GeoModel(
    var latitude: Double,
    var longitude: Double,
    var createdAt: String,
    var workerId: Int? = null,
    var id: Int? = null
)
