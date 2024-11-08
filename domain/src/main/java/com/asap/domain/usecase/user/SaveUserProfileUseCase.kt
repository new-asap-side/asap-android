package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: Int,
        nickname: String,
        profileImg: String
    ) {
        userRepository.saveProfile(userId, nickname, profileImg)
    }
}