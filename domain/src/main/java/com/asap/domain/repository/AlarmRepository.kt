package com.asap.domain.repository

import com.asap.domain.entity.local.DeactivatedAlarm

interface AlarmRepository {
    // 비활성화 알람 조회
    suspend fun getDeactivatedAlarmList(): List<DeactivatedAlarm>

    // 알람 활성화
    suspend fun activate()

    // 알람 비활성화
    suspend fun deactivate()

    // 알람 해제
    suspend fun release()
}