package com.asap.domain.usecase.group

import com.asap.domain.repository.GroupRepository
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class EditPersonalUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        groupId: Int,
        alarmType: String,
        alarmVolume: Int?,
        musicTitle: String?
    ) {
        val userId = userRepository.getUserInfo()?.userId?.toInt()

        groupRepository.postPersonalEdit(
            userId = userId ?: -1,
            groupId = groupId,
            alarmType = alarmType,
            alarmVolume = alarmVolume,
            musicTitle = musicTitle
        )
    }
}