package com.acterics

import com.acterics.service.IpServiceFactory
import com.acterics.service.WeatherServiceFactory

class Application {
    lateinit var ipServiceFactory: IpServiceFactory
    lateinit var weatherServiceFactory: WeatherServiceFactory

    fun run() {
        val ipService = ipServiceFactory.create()
        val weatherService = weatherServiceFactory.create()
        
        val location = ipService.getLocation()

        if (location == null) {
            println("Cannot fetch current location")
            return
        }
        val weather = weatherService.getWeather(location)
        if (weather == null) {
            println("Cannot fetch weather yet")
        } else {
            println("Today temperature is ${weather.main.temp} °C.")
        }        

    }
}