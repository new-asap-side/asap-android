package com.asap.domain.usecase.group

import com.asap.domain.entity.remote.WhetherResponse
import com.asap.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WithdrawGroupUseCase @Inject constructor(
    val groupRepository: GroupRepository
) {
    suspend operator fun invoke(userId: Int, groupId: Int): Flow<WhetherResponse?> {
        return groupRepository.withdrawGroup(userId, groupId)
    }
}