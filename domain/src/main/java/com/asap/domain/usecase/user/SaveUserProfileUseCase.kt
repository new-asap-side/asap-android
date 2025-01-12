package com.asap.domain.usecase.user

import com.asap.domain.entity.remote.user.SaveProfileResponse
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        nickname: String,
        profileImg: String?
    ): SaveProfileResponse? {
        val userId = userRepository.getUserInfo()?.userId?.toInt() ?: -1
        return userRepository.saveProfile(userId, nickname, profileImg ?: "")
    }
}