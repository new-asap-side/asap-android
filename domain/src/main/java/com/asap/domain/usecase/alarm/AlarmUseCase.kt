package com.asap.domain.usecase.alarm

import android.util.Log
import com.asap.domain.entity.local.DeactivatedAlarm
import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.repository.AlarmRepository
import javax.inject.Inject

// 비활성화 알람 리스트 조회
interface GetDeactivatedAlarmListUseCase {
    suspend operator fun invoke(): List<DeactivatedAlarm>
}

class GetDeactivatedAlarmlistUseCaseImpl @Inject constructor(
    private val alarmRepository: AlarmRepository
): GetDeactivatedAlarmListUseCase {
    override suspend fun invoke(): List<DeactivatedAlarm> {
        return alarmRepository.getDeactivatedAlarmList()
    }
}

// 알람 활성화
interface ActivateAlarmUseCase {
    suspend operator fun invoke(alarmSummary: AlarmSummary): Boolean
}

class ActivateAlarmUseCaseImpl @Inject constructor(
    private val alarmRepository: AlarmRepository
): ActivateAlarmUseCase {
    override suspend fun invoke(alarmSummary: AlarmSummary): Boolean {
        return try {
            alarmRepository.activate(alarmSummary)
            true
        } catch (e: Exception) {
            Log.e("ActivateAlarmUseCaseImpl", "$e")
            false
        }
    }

}

// 알람 비활성화
interface DeactivateAlarmUseCase {
    suspend operator fun invoke(alarmSummary: AlarmSummary): Boolean
}

class DeactivateAlarmUseCaseImpl @Inject constructor(
    private val alarmRepository: AlarmRepository
): DeactivateAlarmUseCase {
    override suspend fun invoke(alarmSummary: AlarmSummary): Boolean {
        return try {
            alarmRepository.deactivate(alarmSummary = alarmSummary)
            true
        } catch (e: Exception) {
            Log.e("DeactivateAlarmUseCaseImpl", "$e")
            false
        }
    }
}

