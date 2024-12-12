package com.asap.domain.usecase.group

import com.asap.domain.repository.GroupRepository
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        alarmDay: String,
        alarmEndDate: String,
        alarmTime: String,
        alarmType: String,
        alarmUnlockContents: String,
        alarmVolume: Int,
        description: String,
        deviceToken: String,
        deviceType: String,
        groupPassword: String?,
        isPublic: Boolean,
        maxPerson: Int,
        musicTitle: String,
        title: String,
    ) {
        val userId = userRepository.getUserInfo().userId.toInt()

        groupRepository.postCreateGroup(
            alarmDay = alarmDay,
            alarmEndDate = alarmEndDate,
            alarmTime = alarmTime,
            alarmType = alarmType,
            alarmUnlockContents = alarmUnlockContents,
            alarmVolume = alarmVolume,
            description = description,
            deviceToken = deviceToken,
            deviceType = deviceType,
            groupPassword = groupPassword,
            isPublic = isPublic,
            maxPerson = maxPerson,
            musicTitle = musicTitle,
            title = title,
            userId = userId
        )
    }
}