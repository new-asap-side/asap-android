package com.asap.domain.usecase.group

import com.asap.domain.repository.GroupRepository
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class ReportGroupUseCase @Inject constructor(
    val groupRepository: GroupRepository,
    val userRepository: UserRepository
) {
    suspend operator fun invoke(
        groupId: Int,
        survey: String
    ) {
        val user = userRepository.getUserInfo()
        groupRepository.postReportGroup(
            userId = user?.userId?.toInt() ?: -1,
            groupId = groupId,
            survey = survey
        )
    }
}