package com.asap.domain.repository

import com.asap.domain.entity.local.DeactivatedAlarm
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.alarm.AlarmOffRate
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    // 알람 해제율 조회
    suspend fun fetchAlarmOffRate(): Flow<AlarmOffRate?>

    // 비활성화 알람 조회
    suspend fun getDeactivatedAlarmList(): List<DeactivatedAlarm>

    // 알람 활성화
    suspend fun activate(alarmSummary: AlarmSummary)

    // 알람 비활성화
    suspend fun deactivate(alarmSummary: AlarmSummary)

    // 알람 해제
    suspend fun release(groupId: Int): Flow<WhetherResponse?>
}