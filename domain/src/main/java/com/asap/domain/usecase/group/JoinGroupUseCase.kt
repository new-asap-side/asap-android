package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.GroupJoinResponse
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface JoinGroupUseCase {
    suspend operator fun invoke(body: Map<String, Any>): Flow<GroupJoinResponse?>
}

class JoinGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository
): JoinGroupUseCase {
    override suspend fun invoke(body: Map<String, Any>): Flow<GroupJoinResponse?> {
        return groupRepository.postJoinGroup(body = body)
    }

}