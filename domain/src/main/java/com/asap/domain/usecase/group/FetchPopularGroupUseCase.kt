package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchPopularGroupUseCase {
    suspend operator fun invoke(): Flow<List<AlarmGroup>?>
}

class FetchPopularGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : FetchPopularGroupUseCase {
    override suspend fun invoke(): Flow<List<AlarmGroup>?> {
        return groupRepository.fetchTodayPopularGroup()
    }

}