package com.acterics.libcurl

object API {
    val IP_API = IpApi
    val WEATHER = WeatherApi
}


object IpApi {
    private const val root = "http://ip-api.com"
    const val location = "$root/json"
} 

object WeatherApi {
    private const val root = "https://api.openweathermap.org"
    private const val apikey = "74acba9f0354139a73ccc44351b29d9c"
    private const val version = "2.5"

    fun getWeather(lat: Double, lng: Double): String {
        return "$root/data/$version/weather?lat=$lat&lon=$lng&units=metric&APPID=$apikey"
    }
}