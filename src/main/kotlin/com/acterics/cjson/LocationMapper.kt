package com.acterics.cjson

import cjson.cJSON_Delete as deleteObject
import com.acterics.Location


object LocationMapper {
    fun map(json: String): Location {
        val rootObject = json.parseJson()
        return Location(
                latitude = rootObject["lat"].doubleValue(),
                longitude = rootObject["lon"].doubleValue(),
                city = rootObject["city"].stringValue(),
                country = rootObject["country"].stringValue()
        ).also { deleteObject(rootObject) }

    }
}