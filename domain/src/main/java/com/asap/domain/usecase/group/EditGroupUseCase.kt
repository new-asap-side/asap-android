package com.asap.domain.usecase.group

import com.asap.domain.repository.GroupRepository
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class EditGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        groupId: Int,
        title: String,
        description: String,
        maxPerson: Int,
        alarmUnlockContents: String,
        isPublic: Boolean,
        groupPassword: String?,
        groupImage: String
    ) {
        val userId = userRepository.getUserInfo().userId.toInt()

        groupRepository.postGroupEdit(
            userId = userId,
            groupId = groupId,
            title = title,
            description = description,
            maxPerson = maxPerson,
            alarmUnlockContents = alarmUnlockContents,
            isPublic = isPublic,
            groupPassword = groupPassword,
            groupImage = groupImage
        )
    }
}