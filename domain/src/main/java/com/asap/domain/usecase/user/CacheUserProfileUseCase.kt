package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import javax.inject.Inject

interface CacheUserProfileUseCase {
    suspend operator fun invoke()
}

class CacheUserProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : CacheUserProfileUseCase {
    override suspend fun invoke() {
        TODO("Not yet implemented")
    }

}