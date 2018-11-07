package com.acterics.libcurl

import com.acterics.service.WeatherService
import com.acterics.service.WeatherServiceFactory

import com.acterics.model.Location
import com.acterics.model.Weather

import com.acterics.cjson.WeatherMapper

class CurlWeatherService: WeatherService {
    override fun getWeather(location: Location): Weather? {
        return try {
            fetch(API.WEATHER.getWeather(location.latitude, location.longitude), WeatherMapper::map)
        } catch(e: Exception) {
            println(e)
            null
        }
    }
}

object CurlWeatherServiceFactory: WeatherServiceFactory {
    override fun create(): WeatherService = CurlWeatherService()
}