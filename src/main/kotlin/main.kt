import com.acterics.Location
import kotlinx.cinterop.*
import kotlin.native.concurrent.*
import libcurl.CUrl
import kotlinx.coroutines.*

fun main(args: Array<String>) {
    val fetchedLocation = fetchLocation()
}


fun cache(fileName: String, json: String) {

}


fun fetchLocation(): String {
    println("In fetch location")
    val curl = CUrl("http://ip-api.com/json")
    println("After api curl create")
    var result = ""
    curl.body += { body ->
        result = body
        result
    }
    curl.fetch()
    curl.close()
    return result

}


