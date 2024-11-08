package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        nickname: String,
        profileImg: String?
    ) {
        val userId = userRepository.getUserInfo().userId.toInt()
        
        profileImg?.let {
            userRepository.saveProfile(userId, nickname, it)
        }
    }
}