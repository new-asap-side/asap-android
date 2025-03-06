package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.AlarmGroup
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class FetchGroupUseCase(
    val searchGroupUseCase: SearchGroupUseCase
)

interface SearchGroupUseCase {
    suspend operator fun invoke(query: String): Flow<List<AlarmGroup>>
}

class SearchGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : SearchGroupUseCase {
    override suspend fun invoke(query: String) = groupRepository.searchGroup(query)
}