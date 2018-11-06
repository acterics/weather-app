import com.acterics.Location
import com.acterics.Weather
import com.acterics.API
import com.acterics.cjson.LocationMapper
import com.acterics.cjson.WeatherMapper
import kotlinx.cinterop.*
import kotlin.native.concurrent.*
import libcurl.CUrl
import libcurl.fetch
//import kotlinx.coroutines.*



fun main(args: Array<String>) {
    val location = fetch<Location>(API.IP_API.location, LocationMapper::map)!!
    println("Hello from ${location.city}, ${location.country}")
    val weather = fetch<Weather>(API.WEATHER.getWeather(location.latitude, location.longitude), WeatherMapper::map)
    if (weather == null) {
        println("Cannot fetch weather yet")
    } else {
        println("Today temperature is ${weather.main.temp} °C.")
    }
    


}




