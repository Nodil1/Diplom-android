package com.nodil.diplom.data.repositories

import com.google.gson.Gson
import com.nodil.diplom.domain.models.GeoModel

class LocalGeoRepository(private val storage: SharedPreferencesStorage) {
    private val gsonConverter = Gson()

    fun save(geoModel: GeoModel) {
        if (!storage.contains("geo")) {
            storage.saveString("geo", gsonConverter.toJson(arrayOf<GeoModel>()))
        }
        val geoArray = gsonConverter.fromJson(storage.getString("geo"), Array<GeoModel>::class.java)
            .toMutableList()
        geoArray.add(geoModel)
        storage.saveString("geo", gsonConverter.toJson(geoArray))
    }

    fun getSavedGeo(): Array<GeoModel> {
        if (!storage.contains("geo")){
            return arrayOf<GeoModel>()
        }
        return gsonConverter.fromJson(storage.getString("geo"), Array<GeoModel>::class.java)

    }

    fun clear() {
        storage.saveString("geo", gsonConverter.toJson(arrayOf<GeoModel>()))

    }
}