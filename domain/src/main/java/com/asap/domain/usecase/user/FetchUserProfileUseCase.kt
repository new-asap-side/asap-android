package com.asap.domain.usecase.user

import com.asap.domain.entity.remote.user.UserProfile
import com.asap.domain.repository.UserRepository
import javax.inject.Inject

interface FetchUserProfileUseCase {
    suspend operator fun invoke(): UserProfile?
}

class FetchUserProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchUserProfileUseCase {
    override suspend fun invoke(): UserProfile? {
        return userRepository.fetchUserProfile()
    }

}