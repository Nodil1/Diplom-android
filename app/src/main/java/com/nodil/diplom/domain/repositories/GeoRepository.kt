package com.nodil.diplom.domain.repositories

import com.nodil.diplom.domain.models.GeoModel

interface GeoRepository {
    suspend fun saveGeo(geoModel: GeoModel)
}