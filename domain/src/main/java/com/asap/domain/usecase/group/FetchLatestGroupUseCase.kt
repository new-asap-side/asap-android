package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchLatestGroupUseCase {
    suspend operator fun invoke(): Flow<List<AlarmGroup>?>
}

class FetchLatestGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : FetchLatestGroupUseCase {
    override suspend fun invoke(): Flow<List<AlarmGroup>?> {
        return groupRepository.fetchLatestGroup()
    }
}