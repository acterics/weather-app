package com.acterics.libcurl

import libcurl.curl_easy_init as curlInit
import libcurl.curl_easy_setopt as curtSetOption
import libcurl.curl_easy_cleanup as curlCleanup
import libcurl.curl_easy_perform as curlPerform
import libcurl.curl_easy_strerror as curlGetError
import libcurl.CURLOPT_URL
import libcurl.CURLOPT_HEADERFUNCTION
import libcurl.CURLOPT_HEADERDATA
import libcurl.CURLOPT_WRITEFUNCTION
import libcurl.CURLOPT_WRITEDATA
import libcurl.CURLOPT_NOBODY
import libcurl.CURLE_OK

import kotlinx.cinterop.*
import platform.posix.*
import platform.posix.size_t

class CUrl(val url: String)  {
    val stableRef = StableRef.create(this)

    val curl = curlInit();

    init {
        curtSetOption(curl, CURLOPT_URL, url)
        val header = staticCFunction(::header_callback)
        curtSetOption(curl, CURLOPT_HEADERFUNCTION, header)
        curtSetOption(curl, CURLOPT_HEADERDATA, stableRef.asCPointer())
        val write_data = staticCFunction(::write_callback)
        curtSetOption(curl, CURLOPT_WRITEFUNCTION, write_data)
        curtSetOption(curl, CURLOPT_WRITEDATA, stableRef.asCPointer())
    }

    val header = Event<String>()
    val body = Event<String>()

    fun nobody(){
        curtSetOption(curl, CURLOPT_NOBODY, 1L)
    }

    fun fetch() {
        val res = curlPerform(curl)
        if (res != CURLE_OK) {
            throw NetworkException(curlGetError(res)?.toKString() ?: "Undefiend error")
        }
    }

    fun close() {
        curlCleanup(curl)
        stableRef.dispose()
    }
}

fun CPointer<ByteVar>.toKString(length: Int): String {
    val bytes = this.readBytes(length)
    return bytes.stringFromUtf8()
}

fun header_callback(buffer: CPointer<ByteVar>?, size: size_t, nitems: size_t, userdata: COpaquePointer?): size_t {
    if (buffer == null) return 0u
    if (userdata != null) {
        val header = buffer.toKString((size * nitems).toInt()).trim()
        val curl = userdata.asStableRef<CUrl>().get()
        curl.header(header)
    }
    return size * nitems
}


fun write_callback(buffer: CPointer<ByteVar>?, size: size_t, nitems: size_t, userdata: COpaquePointer?): size_t {
    if (buffer == null) return 0u
    if (userdata != null) {
        val data = buffer.toKString((size * nitems).toInt()).trim()
        val curl = userdata.asStableRef<CUrl>().get()
        curl.body(data)
    }
    return size * nitems
}

typealias Mapper<T> = (json: String) -> T

fun <T>fetch(url: String, mapper: Mapper<T>): T? {
    val curl = CUrl(url)
    var result: T? = null
    curl.body += { body ->
        result = mapper(body)        
    }
    try {
        curl.fetch()
    } catch(e: NetworkException) {
        println("Network exception: ")
        println(e.message)
    } catch(e: Exception) {
        println("Unknown exception: ")
        println(e)
    }
    
    curl.close()
    return result
}

