package com.asap.utility.datetime

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

// 현재 시간을 기준으로 다음 알람 시간까지 남은 시간 계산
interface DateTimeCalculator {
    fun calculate(input: String): Long
}

internal abstract class AljyoDateTimeCalculator : DateTimeCalculator {
    protected abstract val formatter: DateTimeFormatter
    protected abstract val base: Long

    protected fun now(): LocalDateTime = LocalDateTime.now()
}

// (date time) - (월 21:00)
data class DayTime(val day: String, val time: String)

internal class RemainSecondCalculator : AljyoDateTimeCalculator() {
    override val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("EEEE HH:mm", Locale.KOREAN)

    override val base: Long = 24 * 60 * 60

    // input -> ex) 월 화 수 21:00
    override fun calculate(input: String): Long {
        now()
        val dayTimes = mapDayTime(input)
        return 0L
    }

    // 월 화 수 21:00 -> [(월, 21:00), (화, 21:00), (수, 21:00)]
    fun mapDayTime(input: String): List<DayTime> {
        return try {
            input.run {
                split(" ").run {
                    val time = last()
                    subList(0, size - 1).map { day ->
                        DayTime(day, time)
                    }
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

}