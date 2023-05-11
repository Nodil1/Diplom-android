package com.nodil.diplom.domain.usecase.geo

import com.nodil.diplom.domain.models.GeoModel
import com.nodil.diplom.domain.repositories.GeoRepository

class SaveGeoUseCase(
    private val geoRepository: GeoRepository
) {
    suspend fun execute(geoModel: GeoModel) = geoRepository.saveGeo(geoModel)
}