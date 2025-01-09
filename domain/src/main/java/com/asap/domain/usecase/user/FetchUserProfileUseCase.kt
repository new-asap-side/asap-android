package com.asap.domain.usecase.user

import com.asap.domain.entity.remote.user.UserProfile
import com.asap.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FetchUserProfileUseCase {
    suspend operator fun invoke(): Flow<UserProfile?>
}

class FetchUserProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchUserProfileUseCase {
    override suspend fun invoke(): Flow<UserProfile?> {
        return userRepository.fetchUserProfile()
    }

}