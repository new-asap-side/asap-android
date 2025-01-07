package com.asap.domain.repository

interface AlarmRepository {
    // 알람 활성화
    suspend fun activate()

    // 알람 비활성화
    suspend fun deactivate()

    // 알람 해제
    suspend fun release()
}