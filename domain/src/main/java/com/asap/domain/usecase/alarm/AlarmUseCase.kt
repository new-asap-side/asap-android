package com.asap.domain.usecase.alarm

import com.asap.domain.entity.local.DeactivatedAlarm
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

