package com.asap.utility

import com.asap.utility.datetime.RemainSecondCalculator
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DateTimeTest {
    @Test
    fun remainSecondTest() = runTest {
        val deferred = RemainSecondCalculator().run {
            calculate("월 화 수 목 금 토 일", "21:00").also(::println)
        }

        assert(deferred.isActive)
        deferred.await()
        assert(deferred.isCompleted)
    }
}
