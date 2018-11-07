import com.acterics.Application
import com.acterics.libcurl.CurlWeatherServiceFactory
import com.acterics.libcurl.CurlIpServiceFactory
import kotlinx.cinterop.*

//import kotlinx.coroutines.*



fun main(args: Array<String>) {

    val app = Application()
    app.ipServiceFactory = CurlIpServiceFactory
    app.weatherServiceFactory = CurlWeatherServiceFactory

    app.run()
}




