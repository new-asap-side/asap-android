package com.asap.utility.datetime

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

// 현재 시간을 기준으로 다음 알람 시간까지 남은 시간 계산
interface DateTimeCalculator {
    suspend fun calculate(days: String, time: String): Deferred<String>
}

// 알람 시간
// ex) day: "월 화 수", time: 21:00
data class DayTime(val days: String, val time: String)

// extenstions
private fun Long.decimal() = String.format(Locale.KOREAN, "%02d", this)

// Long을 second로 보고 0일 HH:mm:ss로 변환
private fun Long.toDateByColum(): String {
    val basedSecond = 24 * 60 * 60
    val day = this / basedSecond
    val hour = ((this % basedSecond) / 3600).decimal()
    val minite = ((this % 3600) / 60).decimal()
    val second = (this % 60).decimal()

    return if (day == 0L) {
        "$hour:$minite:$second"
    } else {
        "${day}일 $hour:$minite:$second"
    }
}

abstract class BaseDateTimeCalculator(private val scope: CoroutineScope) : DateTimeCalculator {
    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("E HH:mm", Locale.KOREAN)

    // KST
    private val now
        get() = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

    override suspend fun calculate(days: String, time: String): Deferred<String> = scope.async {
        minimumDuration(DayTime(days, time)).toDateByColum()
    }

    abstract fun minimumDuration(dayTime: DayTime): Long

    // input으로 입력 받은 시간과 dayTime 시간 차이
    protected fun convert(input: LocalDateTime = now, dayTime: DayTime): List<Duration> =
        dayTime.run {
            days.split(" ").map { day ->
                val dayOfWeek = DayOfWeek.from(formatter.parse("$day $time"))
                val hm = LocalTime.parse(
                    time,
                    DateTimeFormatter.ofPattern("HH:mm", Locale.KOREAN)
                )

                input.with(dayOfWeek).run {
                    if (isBefore(input)) {
                        // 지난 요일이면 다음 주 같은 요일
                        this.plusWeeks(1)
                    } else if (isEqual(input)) {
                        // 같은 요일이면 현재 시간과 알람 시간을 비교 후
                        // 지난 시간인 경우 다음 주
                        if (isBefore(input.with(hm))) this else this.plusWeeks(1)
                    } else {
                        // 아직 지나지 않은 시간
                        this
                    }
                }.with(hm).let { target ->
                    Duration.between(input, target)
                }
            }
        }
}

internal class RemainSecondCalculator(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) : BaseDateTimeCalculator(scope = scope) {

    override fun minimumDuration(dayTime: DayTime): Long {
        return convert(dayTime = dayTime).map { it.seconds }.minOf { it }
    }

}