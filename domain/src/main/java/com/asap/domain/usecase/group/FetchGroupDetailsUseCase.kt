package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.GroupDetails
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchGroupDetailsUseCase {
    suspend operator fun invoke(groupId: Int): Flow<GroupDetails?>
}

class FetchGroupDetailsUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
) : FetchGroupDetailsUseCase {
    override suspend fun invoke(groupId: Int): Flow<GroupDetails?> {
        return groupRepository.fetchGroupDetails(groupId = groupId)
    }

}