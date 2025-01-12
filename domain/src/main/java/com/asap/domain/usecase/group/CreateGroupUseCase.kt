package com.asap.domain.usecase.group

import com.asap.domain.repository.GroupRepository
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        groupImage: String,
        alarmDay: List<String>,
        alarmEndDate: String,
        alarmTime: String,
        alarmType: String,
        alarmUnlockContents: String,
        alarmVolume: Float?,
        description: String,
        deviceToken: String,
        deviceType: String,
        groupPassword: String?,
        isPublic: Boolean,
        maxPerson: Int,
        musicTitle: String?,
        title: String,
    ): Int? {
        val userId = userRepository.getUserInfo()?.userId?.toInt()

        return groupRepository.postCreateGroup(
            groupImage = groupImage,
            alarmDays = alarmDay,
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
            userId = userId ?: -1
        )
    }
}