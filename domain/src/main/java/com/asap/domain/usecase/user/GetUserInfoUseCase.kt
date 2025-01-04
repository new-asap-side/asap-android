package com.asap.domain.usecase.user

import com.asap.domain.entity.local.User
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

interface GetUserInfoUseCase {
    suspend operator fun invoke(): User?
}

class GetUserInfoUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUserInfoUseCase {
    override suspend fun invoke(): User? {
        return userRepository.getUserInfo()
    }
}