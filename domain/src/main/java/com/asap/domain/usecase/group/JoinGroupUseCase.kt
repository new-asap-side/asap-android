package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.GroupJoinRequest
import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface JoinGroupUseCase {
    suspend operator fun invoke(body: GroupJoinRequest): Flow<GroupJoinResponse?>
}

class JoinGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): JoinGroupUseCase {
    override suspend fun invoke(body: GroupJoinRequest): Flow<GroupJoinResponse?> {
        return groupRepository.postJoinGroup(body = body)
    }

}