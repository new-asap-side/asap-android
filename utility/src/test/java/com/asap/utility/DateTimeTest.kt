package com.asap.utility

import com.asap.utility.datetime.SecondsCalculator
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DateTimeTest {
    @Test
    fun remainSecondTest() = runTest {
        val deferred = SecondsCalculator().run {
            calculate("월 화 수 목 금 토 일", "21:00").also(::println)
        }

        assert(deferred.isActive)
        deferred.await()
        assert(deferred.isCompleted)
    }
}
