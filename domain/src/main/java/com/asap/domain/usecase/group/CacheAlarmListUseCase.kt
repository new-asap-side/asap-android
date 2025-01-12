package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.repository.GroupRepository
import javax.inject.Inject

interface CacheAlarmListUseCase {
    suspend operator fun invoke(alarms: List<AlarmSummary>)
}

class CacheAlarmListUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : CacheAlarmListUseCase {
    override suspend fun invoke(alarms: List<AlarmSummary>) {
        groupRepository.cacheJoinedAlarm(alarms)
    }

}