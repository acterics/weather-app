import com.acterics.Location
import com.acterics.cjson.LocationMapper
import kotlinx.cinterop.*
import kotlin.native.concurrent.*
import libcurl.CUrl
import libcurl.fetch
//import kotlinx.coroutines.*

fun main(args: Array<String>) {
    val currentLocation = fetch<Location>("http://ip-api.com/json", LocationMapper::map)
    println(currentLocation)
}


fun cache(fileName: String, json: String) {

}



