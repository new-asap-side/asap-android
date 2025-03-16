package com.asap.utility

import com.asap.utility.datetime.DayTime
import com.asap.utility.datetime.RemainSecondCalculator
import org.junit.Test

class DateTimeTest {
    @Test
    fun calculator_test() {
        RemainSecondCalculator().run {
            mapDayTime("월 화 수 21:00").also {
                assert(it.size == 3)
                assert(it[0] == DayTime("월", "21:00"))
                assert(it[1] == DayTime("화", "21:00"))
                assert(it[2] == DayTime("수", "21:00"))
            }
        }
    }
}