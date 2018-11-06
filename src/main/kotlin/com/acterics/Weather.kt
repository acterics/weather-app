package com.acterics

data class Weather(
    val main: MainWeatherData
)

data class MainWeatherData(
    val temp: Double,
    val preasure: Double,
    val tempMin: Double,
    val tempMax: Double
)