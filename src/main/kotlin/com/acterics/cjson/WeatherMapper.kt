package com.acterics.cjson

import com.acterics.model.Weather
import com.acterics.model.MainWeatherData
import cjson.cJSON_Delete as deleteObject

object WeatherMapper {
    fun map(json: String): Weather {
        val rootObject = json.parseJson()
        val mainObject = rootObject["main"]!!
        return Weather(
            main = MainWeatherData(
                temp = mainObject["temp"].doubleValue(),
                preasure = mainObject["preasure"].doubleValue(),
                tempMin = mainObject["temp_min"].doubleValue(),
                tempMax = mainObject["temp_max"].doubleValue()
            )
        ).also { deleteObject(rootObject) }
    }
}
