package com.asap.domain.usecase.user

import com.asap.domain.repository.UserRepository
import javax.inject.Inject

interface FetchFCMTokenUseCase {
    suspend operator fun invoke()
}

class FetchFCMTokenUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchFCMTokenUseCase {
    override suspend fun invoke() {
        userRepository.fetchFCMToken()
    }
}