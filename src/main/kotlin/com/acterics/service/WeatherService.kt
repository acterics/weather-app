package com.acterics.service

import com.acterics.model.Location
import com.acterics.model.Weather

interface WeatherService {
    fun getWeather(location: Location): Weather?
}

interface WeatherServiceFactory {
    fun create(): WeatherService
}