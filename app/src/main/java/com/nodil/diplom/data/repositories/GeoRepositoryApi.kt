package com.nodil.diplom.data.repositories

import com.nodil.diplom.domain.models.GeoModel
import com.nodil.diplom.domain.repositories.GeoRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GeoRepositoryApi(private val localGeoRepository: LocalGeoRepository): BaseRepository("geo"), GeoRepository {
    override suspend fun saveGeo(geoModel: GeoModel) {
        uploadCachedGeo()
        uploadGeo(geoModel)
    }

    private suspend fun uploadGeo(geoModel: GeoModel) {
        try {
            val req = client.post("") {
                setBody(geoModel)
            }
        } catch (e: Exception) {
            println("No internet. Save to local")
            localGeoRepository.save(geoModel)
        }
    }
    private suspend fun uploadCachedGeo() {
        val geo = localGeoRepository.getSavedGeo()
        localGeoRepository.clear()
        geo.onEach {
            uploadGeo(it)
        }

    }

}