import com.acterics.model.Location
import com.acterics.model.Weather
import com.acterics.API
import com.acterics.libcurl.fetch
import com.acterics.cjson.LocationMapper
import com.acterics.cjson.WeatherMapper
import kotlinx.cinterop.*

//import kotlinx.coroutines.*



fun main(args: Array<String>) {
    val location = fetch(API.IP_API.location, LocationMapper::map)
    println("Hello from ${location?.city}, ${location?.country}")
    val weather: Weather? = location?.let { fetch(API.WEATHER.getWeather(location.latitude, location.longitude), WeatherMapper::map) }
    if (weather == null) {
        println("Cannot fetch weather yet")
    } else {
        println("Today temperature is ${weather.main.temp} °C.")
    }
    


}




