package com.asap.data.utility

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

object DateTimeManager {
    private const val BASED_MINITE = 7 * 24 * 60
    private const val BASED_SECOND = 7 * 24 * 60 * 60

    private const val DAY_BY_SECOND = 86400

    // ex) 월요일 13:30
    private val dayFormatter = DateTimeFormatter.ofPattern("EEEE HH:mm", Locale.KOREAN)

    // ex) 13:30
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)

    private fun formatCurrentTime(): String {
        val now = LocalDateTime.now()
        return now.format(dayFormatter)
    }

    fun parseISO(stringDate: String): String {
        return ZonedDateTime.parse(stringDate).toLocalDate().toString()
    }

    fun parseToAmPm(time: String): String {
        val splitedTime = time.split(":")
        val hour = splitedTime[0].toInt()
        val minites = splitedTime[1]

        val prefix = if ((hour / 12) > 0) "오후" else "오전"
        return "$prefix ${hour % 12}:$minites"
    }

    // 현재 요일을 기준으로
    // input 정렬
    fun sortByDay(input: List<String>, isTest: Boolean = false): List<String> {
        val today = if (isTest) {
            parseToDayOfWeek("월")
        } else {
            parseToDayOfWeek(formatCurrentTime().split(" ")[0])
        }

        return input.sortedWith(
            compareBy(
                { parseDayOfWeek(today, it.split(" ")[0]) },
                { LocalTime.parse(it.split(" ")[1], timeFormatter) }
            )
        )
    }

    // 현재 시간을 기준으로
    // input 시간까지 남은 시간을 분 단위로 반환
    fun diffFromNow(input: String, isTest: Boolean = false, basedMinite: Boolean = true): Long {
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

        if (basedMinite) {
            // '분' 기준
            val duration = ChronoUnit.MINUTES.between(now, targetDateTime)
            return if (duration < 0L) BASED_MINITE + duration else duration
        } else {
            // '초' 기준
            val duration = ChronoUnit.SECONDS.between(now, targetDateTime)
            return if (duration < 0L) BASED_SECOND + duration else duration
        }
    }

    // duration 60 -> 01시간 00분
    fun parseToDay(duration: Long): String {
        val days = (duration / 60) / 24
        val hours = String.format(
            Locale.KOREAN,
            "%02d", (duration / 60) % 24
        )
        val minites = String.format(
            Locale.KOREAN,
            "%02d", duration % 60
        )

        return if (days == 0L) {
            "${hours}시간 ${minites}분"
        } else {
            "${days}일 ${hours}시간 ${minites}분"
        }
    }

    fun parseToDayBySecond(duration: Long): String {
        val days = duration / DAY_BY_SECOND
        val hours = String.format(
            Locale.KOREAN,
            "%02d", (duration % DAY_BY_SECOND) / 3600
        )
        val minites = String.format(
            Locale.KOREAN,
            "%02d", (duration % 3600) / 60
        )
        val seconds = String.format(
            Locale.KOREAN,
            "%02d", (duration % 60)
        )

        return if (days == 0L) {
            return if (hours == "00")
                "${minites}분 ${seconds}초"
            else
                "${hours}시간 ${minites}분 ${seconds}초"
        } else {
            "${days}일 ${hours}시간 ${minites}분 ${seconds}초"
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