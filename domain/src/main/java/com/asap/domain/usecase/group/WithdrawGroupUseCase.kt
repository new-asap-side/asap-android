package com.asap.domain.usecase.group

import com.asap.domain.repository.GroupRepository
import javax.inject.Inject

class WithdrawGroupUseCase @Inject constructor(
    val groupRepository: GroupRepository
) {
    suspend operator fun invoke(userId: Int, groupId: Int) {
        groupRepository.withdrawGroup(userId, groupId)
    }
}