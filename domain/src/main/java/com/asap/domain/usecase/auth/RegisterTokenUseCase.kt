package com.asap.domain.usecase.auth

import com.asap.domain.repository.AuthRepository
import javax.inject.Inject

interface RegisterTokenUseCase {
    suspend operator fun invoke()
}

class RegisterTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : RegisterTokenUseCase {
    override suspend fun invoke() {
        authRepository.registerToken()
    }

}