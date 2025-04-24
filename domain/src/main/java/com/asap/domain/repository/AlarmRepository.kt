package com.asap.domain.repository

import com.asap.domain.entity.local.AlarmEntity
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.entity.remote.alarm.AlarmOffRate
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {
    // FCM token 동기화
    suspend fun patchAlarmToken(token: String): Boolean

    // 알람 해제율 조회
    suspend fun fetchAlarmOffRate(): Flow<AlarmOffRate?>

    // 비활성화 알람 조회
    suspend fun getDeactivatedAlarmList(): List<AlarmEntity>

    // 알람 활성화
    suspend fun activate(alarmSummary: AlarmSummary)

    // 알람 비활성화
    suspend fun deactivate(alarmSummary: AlarmSummary)

    // 알람 해제
    suspend fun release(groupId: Int): Flow<WhetherResponse?>
}