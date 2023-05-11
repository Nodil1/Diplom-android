package com.nodil.diplom.data.repositories

import com.nodil.diplom.domain.models.GeoModel
import com.nodil.diplom.domain.repositories.GeoRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class GeoRepositoryApi: BaseRepository("geo"), GeoRepository {
    override suspend fun saveGeo(geoModel: GeoModel) {
        val req = client.post("") {
            setBody(geoModel)
        }

    }
}