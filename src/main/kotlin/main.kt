import kotlinx.cinterop.*
import libcurl.CUrl

fun main(args: Array<String>) {

    val curl = CUrl("http://ip-api.com/json")

    curl.body += { body ->
        println(body)
    }

    println("Hello, my lovely Nastya")
}
