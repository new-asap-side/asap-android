package com.asap.domain.usecase.group

import com.asap.domain.entity.local.User
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User? {
        return userRepository.getUserInfo()
    }
}