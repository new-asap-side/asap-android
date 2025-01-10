package com.asap.domain.usecase.auth

import com.asap.domain.repository.AuthRepository
import javax.inject.Inject

interface RefreshTokenUseCase {
    suspend operator fun invoke(): Boolean
}

class RefreshTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : RefreshTokenUseCase {
    override suspend fun invoke(): Boolean = authRepository.refreshToken()
}