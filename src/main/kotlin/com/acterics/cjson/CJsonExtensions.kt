package com.acterics.cjson

import cjson.cJSON_Parse as parseJson
import cjson.cJSON_GetErrorPtr as getErrorPointer
import cjson.cJSON_GetObjectItemCaseSensitive as jsonObjectItem
import cjson.cJSON_GetStringValue as jsonStringValue
import cjson.cJSON


import kotlinx.cinterop.CPointer
import kotlinx.cinterop.toKString
import kotlinx.cinterop.pointed


internal fun CPointer<cJSON>?.intValue() = this?.pointed?.valueint ?: 0

internal fun CPointer<cJSON>?.doubleValue() = this?.pointed?.valuedouble ?: 0.0

internal fun CPointer<cJSON>?.stringValue() = jsonStringValue(this)?.toKString() ?: ""

internal fun <T>CPointer<cJSON>?.objectValue(mapper: (String) -> T): T? = this?.let { mapper(it.stringValue()) }

internal operator fun CPointer<cJSON>.get(key: String): CPointer<cJSON>? {
    return jsonObjectItem(this, key)
}



internal fun String.parseJson(): CPointer<cJSON> {
    val cJson = parseJson(this)
    val errorMsg = getErrorPointer()?.toKString() ?: ""
    if (errorMsg.isNotEmpty()) {
        throw JsonParseException("JSON error before: $errorMsg\n")
    }
    return cJson!!
}