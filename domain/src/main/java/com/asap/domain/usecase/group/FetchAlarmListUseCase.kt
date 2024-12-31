package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.AlarmSummary
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchAlarmListUseCase {
    suspend operator fun invoke(userId: Int): Flow<List<AlarmSummary>?>
}

class FetchAlarmListUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): FetchAlarmListUseCase {
    override suspend fun invoke(userId: Int): Flow<List<AlarmSummary>?> {
        return groupRepository.fetchUserAlarmList(userId = userId)
    }
}