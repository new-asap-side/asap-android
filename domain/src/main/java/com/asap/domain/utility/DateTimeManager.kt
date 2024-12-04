package com.asap.domain.utility

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object DateTimeManager {
    // ex) 월요일 13:30
    private val dayFormatter = DateTimeFormatter.ofPattern("EEEE HH:mm", Locale.KOREAN)

    // ex) 13:30
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)

    fun formatCurrentTime(): String {
        val now = LocalDateTime.now()
        return now.format(dayFormatter)
    }

    fun diffFromNow(input: String, isTest: Boolean = false): Long {
        val today = if (isTest) {
            parseToDayOfWeek("월요일")
        } else {
            parseToDayOfWeek(formatCurrentTime().split(" ")[0])
        }

        val startOfWeek = LocalDateTime.now().with(today)

        val target = input.split(" ")
        val targetDateTime = LocalDateTime.of(
            startOfWeek.plusDays(parseDayOfWeek(today, target[0])).toLocalDate(),
            LocalTime.parse(target[1], timeFormatter)
        )

        val now = if (isTest) {
            LocalDateTime.of(
                startOfWeek.plusDays(parseDayOfWeek(today, "월요일")).toLocalDate(),
                LocalTime.parse("12:30", timeFormatter)
            )
        } else {
            LocalDateTime.now()
        }

        return ChronoUnit.MINUTES.between(now, targetDateTime)
    }

    fun parseToDay(duration: Long): String {
        return if (duration < 0) {
            // 현재 시간이 12:00 일 경우
            // 다음 주 같은 날짜에 12:00 이전 시간에 대한 처리
            val remainTime = 24 * 60 + duration
            val hours = remainTime / 60
            val minites = remainTime % 60

            "6일 ${hours}시간 ${minites}분"
        } else {
            val days = (duration / 60) / 24
            val hours = (duration / 60) % 24
            val minites = duration % 60

            "${days}일 ${hours}시간 ${minites}분"
        }
    }

    private fun parseToDayOfWeek(day: String): DayOfWeek = when (day) {
        "일", "일요일" -> DayOfWeek.SUNDAY
        "월", "월요일" -> DayOfWeek.MONDAY
        "화", "화요일" -> DayOfWeek.TUESDAY
        "수", "수요일" -> DayOfWeek.WEDNESDAY
        "목", "목요일" -> DayOfWeek.THURSDAY
        "금", "금요일" -> DayOfWeek.FRIDAY
        "토", "토요일" -> DayOfWeek.SATURDAY
        else -> throw IllegalArgumentException("Unknown Day: $day")
    }

    private fun parseDayOfWeek(baseDay: DayOfWeek, day: String): Long {
        val target = when (day) {
            "일", "일요일" -> 0
            "월", "월요일" -> 1
            "화", "화요일" -> 2
            "수", "수요일" -> 3
            "목", "목요일" -> 4
            "금", "금요일" -> 5
            "토", "토요일" -> 6
            else -> throw IllegalArgumentException("Unknown Day: $day")
        }

        val base = baseDay.value.toLong()
        return (target - base + 7) % 7
    }
}