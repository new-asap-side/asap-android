package com.asap.aljyo.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.format(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    return format(formatter)
}