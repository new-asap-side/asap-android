package com.asap.domain.usecase.auth

import com.asap.domain.repository.AuthRepository
import javax.inject.Inject

interface CheckCachedAuthUseCase {
    suspend operator fun invoke(): Boolean
}

class CheckAuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : CheckCachedAuthUseCase {
    override suspend fun invoke(): Boolean {
        return authRepository.checkCachedAuth()
    }
}