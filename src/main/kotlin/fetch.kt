typealias Mapper<T>: (json: String) -> T

fun fetch<T>(url: String, mapper: Mapper<T>): T {
    val curl = CUrl(url)
    var result: T? = null
    curl.body += { body ->
        result = mapper(body)
    }
    curl.fetch()
    curl.close()
    return result
}
