package com.acterics.libcurl

import com.acterics.service.WeatherService
import com.acterics.service.WeatherServiceFactory

import com.acterics.model.Location
import com.acterics.model.Weather

import com.acterics.cjson.WeatherMapper

class CurlWeatherService: WeatherService {

    companion object {
        private const val ROOT = "https://api.openweathermap.org"
        private const val VERSION = "2.5"
        private const val UNITS = "metric"
        private const val API_KEY = "74acba9f0354139a73ccc44351b29d9c"
    }

    override fun getWeather(location: Location): Weather? {
        return try {
            fetch(getWeatherPath(location), WeatherMapper::map)
        } catch(e: Exception) {
            println(e)
            null
        }
    }

    private fun getWeatherPath(location: Location): String {
        return "$ROOT/data/$VERSION/weather?lat=${location.latitude}&lon=${location.longitude}&units=$UNITS&APPID=$API_KEY"
    }
}

object CurlWeatherServiceFactory: WeatherServiceFactory {
    override fun create(): WeatherService = CurlWeatherService()
}