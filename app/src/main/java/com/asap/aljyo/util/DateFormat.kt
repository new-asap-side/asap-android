package com.asap.aljyo.util

import java.time.LocalDate
import java.time.ZoneOffset

fun LocalDate.format(): String = atTime(23,59).atZone(ZoneOffset.UTC).toString()