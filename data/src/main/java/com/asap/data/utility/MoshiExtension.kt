package com.asap.data.utility

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

fun <K, V> Moshi.mapToJson(key: Class<K>, value: Class<V>, raw: Any): String {
    return adapter<Any>(Types.newParameterizedType(Map::class.java, key, value)).toJson(raw)
}