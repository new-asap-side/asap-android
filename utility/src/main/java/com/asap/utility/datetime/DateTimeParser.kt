package com.asap.utility.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

interface DateTimeParser {
    fun parse(): String
}

abstract class AljyoDateTimeFormatter {
    protected abstract val formatter: DateTimeFormatter

    protected fun now(): LocalDateTime = LocalDateTime.now()
}

// 현재 시간 기준으로 yyyy.MM.dd format
internal class TimeDotParser : AljyoDateTimeFormatter(), DateTimeParser {
    override val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREA)

    override fun parse(): String = now().run {
        format(formatter)
    }
}

// 현재 시간 기준 HH:mm format
internal class TimeColonParser : AljyoDateTimeFormatter(), DateTimeParser {
    override val formatter: DateTimeFormatter
        get() = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREA)

    override fun parse(): String = now().run {
        format(formatter)
    }
}