package com.asap.data.utility

import org.junit.Test

class UtilityTest {
    @Test
    fun test_DateTimeManager_diff() {
        // 테스트 시 현재 시간은 월요일 12:30으로 고정됨
        val nextDay1 = "화 09:30"
        val output1 = DateTimeManager.diffFromNow(nextDay1, isTest = true)
        val outputDate1 = DateTimeManager.parseToDay(output1)

        assert(output1 == 1260L)
        assert(outputDate1 == "21시간 00분")

        val nextDay2 = "화 20:25"
        val output2 = DateTimeManager.diffFromNow(nextDay2, isTest = true)
        val outputDate2 = DateTimeManager.parseToDay(output2)

        assert(output2 == 1915L)
        assert(outputDate2 == "1일 07시간 55분")

        val nextDay3 = "일 11:22"
        val output3 = DateTimeManager.diffFromNow(nextDay3, isTest = true)
        val outputDate3 = DateTimeManager.parseToDay(output3)

        assert(output3 == 8572L)
        assert(outputDate3 == "5일 22시간 52분")

        val nextDay4 = "일 17:55"
        val output4 = DateTimeManager.diffFromNow(nextDay4, isTest = true)
        val outputDate4 = DateTimeManager.parseToDay(output4)

        assert(output4 == 8965L)
        assert(outputDate4 == "6일 05시간 25분")


        val nextDay5 = "월 11:30"
        val output5 = DateTimeManager.diffFromNow(nextDay5, isTest = true)
        val outputDate5 = DateTimeManager.parseToDay(output5)

        assert(output5 == 10020L)
        assert(outputDate5 == "6일 23시간 00분")

        val nextDay6 = "월 17:45"
        val output6 = DateTimeManager.diffFromNow(nextDay6, isTest = true)
        val outputDate6 = DateTimeManager.parseToDay(output6)

        assert(output6 == 315L)
        assert(outputDate6 == "05시간 15분")
    }

    @Test
    fun test_DateTimeManager_sortByDay() {
        // 테스트 시 기준 요일 월요일로 고정
        val days = listOf(
            "일 11:30",
            "수 17:30",
            "수 12:30",
            "목 11:30",
            "월 11:30",
            "화 11:30",
            "토 11:30",
        )

        val sortedDays = DateTimeManager.sortByDay(days, isTest = true)
        assert(sortedDays[0] == "월 11:30")
    }

    @Test
    fun test_DateTimeManager_parseToDate() {
        val stringDateISO = "2024-12-31T23:59:59Z"
        val stringDate = DateTimeManager.parseToDate(stringDateISO)

        assert(stringDate == "2024-12-31")
    }
}